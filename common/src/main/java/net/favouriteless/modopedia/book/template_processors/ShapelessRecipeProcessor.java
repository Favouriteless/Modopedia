package net.favouriteless.modopedia.book.template_processors;

import net.favouriteless.modopedia.api.Lookup.MutableLookup;
import net.favouriteless.modopedia.api.Variable;
import net.favouriteless.modopedia.api.books.Book;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class ShapelessRecipeProcessor extends CraftingTableRecipeProcessor {

    @Override
    public void init(Book book, MutableLookup lookup, Level level) {
        super.init(book, lookup, level);
        ResourceLocation id = lookup.get("recipe").as(ResourceLocation.class);

        RecipeHolder<?> holder = level.getRecipeManager().byKey(id).orElseThrow();

        if(holder.value() instanceof ShapelessRecipe recipe) {
            List<ItemStack[]> inputs = new ArrayList<>();
            recipe.getIngredients().forEach(i -> inputs.add(i.getItems()));

            lookup.set("inputs", Variable.of(inputs));
            lookup.set("output", Variable.of(List.<ItemStack[]>of(new ItemStack[] { recipe.getResultItem(level.registryAccess()) })));
        }
        else {
            throw new IllegalArgumentException("Shapeless recipe template must use a Shapeless recipe.");
        }
    }

}
