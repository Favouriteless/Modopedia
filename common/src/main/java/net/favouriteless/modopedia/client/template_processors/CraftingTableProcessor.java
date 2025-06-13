package net.favouriteless.modopedia.client.template_processors;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.Lookup.MutableLookup;
import net.favouriteless.modopedia.api.Variable;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookTexture;
import net.favouriteless.modopedia.api.book.BookTexture.Rectangle;
import net.favouriteless.modopedia.api.book.TemplateProcessor;
import net.favouriteless.modopedia.api.registries.client.BookTextureRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class CraftingTableProcessor implements TemplateProcessor {

    public static final int GRID_WIDTH = 50;

    @Override
    public void init(Book book, MutableLookup lookup, Level level) {
        initComponents(book, lookup, level);
        initRecipe(book, lookup, level);
    }

    protected void initRecipe(Book book, MutableLookup lookup, Level level) {
        ResourceLocation id = lookup.get("recipe").as(ResourceLocation.class);
        RecipeHolder<?> holder = level.getRecipeManager().byKey(id).orElseThrow();

        if(holder.value() instanceof ShapelessRecipe recipe) {
            List<ItemStack[]> inputs = new ArrayList<>();
            recipe.getIngredients().forEach(i -> inputs.add(i.getItems()));

            lookup.set("inputs", Variable.of(inputs));
            lookup.set("output", Variable.of(List.<ItemStack[]>of(new ItemStack[] { recipe.getResultItem(level.registryAccess()) })));
            lookup.set("tooltip", Variable.of(List.of(Modopedia.translation("template", "shapeless_recipe"))));
        }
        else if(holder.value() instanceof ShapedRecipe recipe) {
            List<ItemStack[]> inputs = new ArrayList<>();
            recipe.getIngredients().forEach(i -> inputs.add(i.getItems()));

            if(recipe.getWidth() == 2) {
                int rows = recipe.getHeight();
                for(int i = 0; i < rows; i++) {
                    inputs.add(i*3 + 2, new ItemStack[0]);
                }
            }

            lookup.set("inputs", Variable.of(inputs));
            lookup.set("output", Variable.of(List.<ItemStack[]>of(new ItemStack[] { recipe.getResultItem(level.registryAccess()) })));
            lookup.set("tooltip", Variable.of(List.of(Modopedia.translation("template", "shaped_recipe"))));
        }

        else {
            throw new IllegalArgumentException("CraftingTableRecipe template must have a valid crafting table recipe.");
        }
    }

    protected void initComponents(Book book, MutableLookup lookup, Level level) {
        BookTexture tex = BookTextureRegistry.get().getTexture(book.getTexture());
        if(tex == null)
            throw new IllegalStateException("CraftingTableRecipe templates require the book to have a valid BookTexture");

        Rectangle grid = tex.widgets().get("crafting_grid");
        Rectangle arrow = tex.widgets().get("crafting_arrow");
        Rectangle frame = tex.widgets().get("small_frame");

        if(grid == null || arrow == null || frame == null)
            throw new IllegalStateException("CraftingTableRecipe templates require the BookTexture to have crafting_grid, crafting_arrow and small_frame widgets");

        int arrowX = GRID_WIDTH + ((grid.width() - GRID_WIDTH) / 2) + 2;

        lookup.set("arrow_x", Variable.of(arrowX));
        lookup.set("arrow_y", Variable.of(GRID_WIDTH / 2 - arrow.height() / 2)); // Grid offset ignored as (0,0) is the first item, not the frame
        lookup.set("arrow_width", Variable.of(arrow.width()));
        lookup.set("arrow_height", Variable.of(arrow.height()));

        lookup.set("output_x", Variable.of(arrowX + arrow.width() + 2 + (frame.width() - 16) / 2));
    }

}
