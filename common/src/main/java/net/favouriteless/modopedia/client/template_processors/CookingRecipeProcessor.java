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
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CookingRecipeProcessor implements TemplateProcessor {

    public static final ResourceLocation ID = Modopedia.id("cooking_recipe");
    public static final Map<Class<? extends AbstractCookingRecipe>, String> TYPE_KEYS = new HashMap<>();

    static {
        TYPE_KEYS.put(BlastingRecipe.class, Modopedia.translation("template", "blasting_recipe"));
        TYPE_KEYS.put(SmeltingRecipe.class, Modopedia.translation("template", "smelting_recipe"));
        TYPE_KEYS.put(SmokingRecipe.class, Modopedia.translation("template", "smoking_recipe"));
    }

    @Override
    public void init(Book book, MutableLookup lookup, Level level) {
        initComponents(book, lookup, level);
        initRecipe(book, lookup, level);
    }

    protected void initRecipe(Book book, MutableLookup lookup, Level level) {
        ResourceLocation id = lookup.get("recipe").as(ResourceLocation.class);

        Optional<RecipeHolder<?>> optional = level.getRecipeManager().byKey(id);
        if(optional.isEmpty())
            throw new IllegalArgumentException(id + " is not a valid recipe.");

        Recipe<?> recipe = optional.get().value();

        if(!TYPE_KEYS.containsKey(recipe.getClass()))
            throw new IllegalStateException("CookingRecipe template must use a valid cooking recipe");

        lookup.set("p_tooltip", Variable.of(List.of(TYPE_KEYS.get(recipe.getClass()))));
        lookup.set("p_inputs", Variable.of(List.of(List.of(recipe.getIngredients().getFirst().getItems()))));
        lookup.set("p_output", Variable.of(List.of(List.of(recipe.getResultItem(level.registryAccess())))));
    }

    protected void initComponents(Book book, MutableLookup lookup, Level level) {
        BookTexture tex = BookTextureRegistry.get().getTexture(book.getTexture());
        if(tex == null)
            throw new IllegalStateException("Crafting grid templates require the book to have a valid BookTexture");

        Rectangle page = tex.pages().get(lookup.get("page_num").asInt() % tex.pages().size());
        Rectangle frame = tex.widgets().get("small_frame");
        Rectangle arrow = tex.widgets().get("crafting_arrow");
        Rectangle flame = tex.widgets().get("crafting_flame");

        if(flame == null || arrow == null || frame == null)
            throw new IllegalStateException("Cooking recipe templates require the BookTexture to have crafting_flame, crafting_arrow and small_frame widgets");

        int spacing = (frame.width() - 16 ) / 2;

        int arrowX = 16 + spacing + 4;
        int arrowY = 8 - arrow.height() / 2;
        int flameX = arrowX + arrow.width()/2 - flame.width()/2;
        int flameY = arrowY + arrow.height() + 2;

        int outputX = arrowX + arrow.width() + 4 + spacing;

        int offset = (page.width() - (outputX + 16)) / 2; // This should self-center the components.

        lookup.set("p_input_x", Variable.of(offset));

        lookup.set("p_arrow_x", Variable.of(arrowX + offset));
        lookup.set("p_arrow_y", Variable.of(arrowY));
        lookup.set("p_arrow_width", Variable.of(arrow.width()));
        lookup.set("p_arrow_height", Variable.of(arrow.height()));

        lookup.set("p_flame_x", Variable.of(flameX + offset));
        lookup.set("p_flame_y", Variable.of(flameY));

        lookup.set("p_output_x", Variable.of(outputX + offset));
    }

}
