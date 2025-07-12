package net.favouriteless.modopedia.client.template_processors;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.Lookup.MutableLookup;
import net.favouriteless.modopedia.api.Variable;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookTexture;
import net.favouriteless.modopedia.api.book.BookTexture.Rectangle;
import net.favouriteless.modopedia.api.book.TemplateProcessor;
import net.favouriteless.modopedia.api.book.page_components.ItemDisplay;
import net.favouriteless.modopedia.api.registries.client.BookTextureRegistry;
import net.favouriteless.modopedia.client.page_components.item_displays.CyclingItemDisplay;
import net.favouriteless.modopedia.client.page_components.item_displays.EmptyItemDisplay;
import net.favouriteless.modopedia.client.page_components.item_displays.GridItemDisplay;
import net.favouriteless.modopedia.client.page_components.item_displays.SimpleItemDisplay;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CraftingRecipeProcessor implements TemplateProcessor {

    public static final ResourceLocation ID = Modopedia.id("crafting_recipe");
    public static final int GRID_WIDTH = 50;

    @Override
    public void init(Book book, MutableLookup lookup, Level level) {
        initComponents(book, lookup);
        initRecipe(lookup, level);
    }

    protected void initRecipe(MutableLookup lookup, Level level) {
        ResourceLocation id = lookup.get("recipe").as(ResourceLocation.class);

        Optional<RecipeHolder<?>> optional = level.getRecipeManager().byKey(id);
        if(optional.isEmpty())
            throw new IllegalArgumentException(id + " is not a valid recipe.");

        RecipeHolder<?> holder = optional.get();

        if(holder.value() instanceof ShapelessRecipe recipe) {
            List<ItemDisplay> displays = recipe.getIngredients().stream()
                    .<ItemDisplay>map(i -> new CyclingItemDisplay(Arrays.asList(i.getItems())))
                    .toList();

            lookup.set("p_inputs", Variable.of(new GridItemDisplay(displays, 3, 17, false)));
            lookup.set("p_output", Variable.of(new SimpleItemDisplay(recipe.getResultItem(level.registryAccess()))));
            lookup.set("p_tooltip", Variable.of(List.of(Modopedia.translation("template", "shapeless_recipe"))));
        }
        else if(holder.value() instanceof ShapedRecipe recipe) {
            NonNullList<Ingredient> ingredients = recipe.getIngredients();
            List<ItemDisplay> displays = new ArrayList<>();

            int rows = recipe.getHeight();
            int columns = recipe.getWidth();
            for(int y = 0; y < rows; y++) {
                for(int x = 0; x < columns; x++) {
                    ItemStack[] items = ingredients.get(x + y*columns).getItems();
                    displays.add(items.length == 0 ? new EmptyItemDisplay() : new CyclingItemDisplay(Arrays.asList(items)));
                }
                for(int i = 0; i < 3 - columns; i++)
                    displays.add(new EmptyItemDisplay());
            }

            lookup.set("p_inputs", Variable.of(new GridItemDisplay(displays, 3, 17, false)));
            lookup.set("p_output", Variable.of(new SimpleItemDisplay(recipe.getResultItem(level.registryAccess()))));
            lookup.set("p_tooltip", Variable.of(List.of(Modopedia.translation("template", "shaped_recipe"))));
        }
        else {
            throw new IllegalArgumentException("CraftingRecipe template must have a valid crafting table recipe.");
        }
    }

    protected void initComponents(Book book, MutableLookup lookup) {
        BookTexture tex = BookTextureRegistry.get().getTexture(book.getTexture());
        if(tex == null)
            throw new IllegalStateException("CraftingRecipe templates require the book to have a valid BookTexture");

        Rectangle page = tex.pages().get(lookup.get("page_num").asInt() % tex.pages().size());
        Rectangle grid = tex.widgets().get("crafting_grid");
        Rectangle arrow = tex.widgets().get("crafting_arrow");
        Rectangle frame = tex.widgets().get("small_frame");

        if(grid == null || arrow == null || frame == null)
            throw new IllegalStateException("CraftingRecipe templates require the BookTexture to have crafting_grid, crafting_arrow and small_frame widgets");

        int arrowX = GRID_WIDTH + ((grid.width() - GRID_WIDTH) / 2) + 2;
        int arrowY = GRID_WIDTH / 2 - arrow.height() / 2;
        int outputX = arrowX + arrow.width() + 2 + (frame.width() - 16) / 2;

        int offset = (page.width() - (outputX + 16)) / 2; // This should self-center the components.

        lookup.set("p_grid_x", Variable.of(offset));
        lookup.set("p_arrow_x", Variable.of(arrowX + offset));
        lookup.set("p_arrow_y", Variable.of(arrowY)); // Grid offset ignored as (0,0) is the first item, not the frame
        lookup.set("p_arrow_width", Variable.of(arrow.width()));
        lookup.set("p_arrow_height", Variable.of(arrow.height()));
        lookup.set("p_output_x", Variable.of(outputX + offset));
    }

}
