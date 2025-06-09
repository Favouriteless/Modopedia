package net.favouriteless.modopedia.book.template_processors;

import net.favouriteless.modopedia.api.Lookup.MutableLookup;
import net.favouriteless.modopedia.api.Variable;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.TemplateProcessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class ShapedRecipeProcessor implements TemplateProcessor {

    @Override
    public void init(Book book, MutableLookup lookup, Level level) {
        ResourceLocation id = lookup.get("recipe").as(ResourceLocation.class);

        RecipeHolder<?> holder = level.getRecipeManager().byKey(id).orElseThrow();

        if(holder.value() instanceof ShapedRecipe recipe) {
            List<ItemStack[]> inputs = new ArrayList<>();
            recipe.getIngredients().forEach(i -> inputs.add(i.getItems()));

            if(recipe.getWidth() == 2) {
                int rows = recipe.getHeight();
                for(int i = 0; i < rows; i++) {
                    inputs.add(i*3 + 2, new ItemStack[0]);
                }
            }

            lookup.set("inputs", Variable.of(inputs));
            lookup.set("output", Variable.of(recipe.getResultItem(level.registryAccess())));
        }
        else {
            throw new IllegalArgumentException("Shaped recipe template must use a shaped recipe.");
        }
    }

}
