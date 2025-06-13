package net.favouriteless.modopedia.client.template_processors;

import net.favouriteless.modopedia.api.Lookup.MutableLookup;
import net.favouriteless.modopedia.api.Variable;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookTexture;
import net.favouriteless.modopedia.api.book.BookTexture.Rectangle;
import net.favouriteless.modopedia.api.book.TemplateProcessor;
import net.favouriteless.modopedia.api.registries.client.BookTextureRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

import java.util.List;

public class CookingRecipeProcessor implements TemplateProcessor {

    @Override
    public void init(Book book, MutableLookup lookup, Level level) {
        ResourceLocation id = lookup.get("recipe").as(ResourceLocation.class);
        RecipeHolder<?> holder = level.getRecipeManager().byKey(id).orElseThrow();

        if(holder.value() instanceof AbstractCookingRecipe recipe) {
            lookup.set("input", Variable.of(List.<ItemStack[]>of(recipe.getIngredients().getFirst().getItems())));
            lookup.set("output", Variable.of(List.<ItemStack[]>of(new ItemStack[] { recipe.getResultItem(level.registryAccess()) })));
        }
        else {
            throw new IllegalArgumentException("Cooking recipe template must use a cooking recipe.");
        }

        BookTexture tex = BookTextureRegistry.get().getTexture(book.getTexture());
        if(tex == null)
            throw new IllegalStateException("Crafting grid templates require the book to have a valid BookTexture");

        Rectangle frame = tex.widgets().get("small_frame");
        Rectangle arrow = tex.widgets().get("crafting_arrow");
        Rectangle flame = tex.widgets().get("crafting_flame");

        if(flame == null || arrow == null || frame == null)
            throw new IllegalStateException("Cooking recipe templates require the BookTexture to have crafting_flame, crafting_arrow and small_frame widgets");

        int spacing = (frame.width() - 16 ) / 2;
        int flameX = 16 + spacing + 2;
        int arrowX = flameX + flame.width() + 2;

        lookup.set("flame_x", Variable.of(flameX));
        lookup.set("flame_y", Variable.of(8 - flame.height() / 2));

        lookup.set("arrow_x", Variable.of(arrowX));
        lookup.set("arrow_y", Variable.of(8 - arrow.height() / 2));
        lookup.set("arrow_width", Variable.of(arrow.width()));
        lookup.set("arrow_height", Variable.of(arrow.height()));

        lookup.set("output_x", Variable.of(arrowX + arrow.width() + 2 + spacing));
    }

}
