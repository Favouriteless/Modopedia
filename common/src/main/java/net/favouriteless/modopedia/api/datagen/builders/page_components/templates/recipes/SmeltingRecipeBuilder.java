package net.favouriteless.modopedia.api.datagen.builders.page_components.templates.recipes;

import net.favouriteless.modopedia.Modopedia;
import net.minecraft.resources.ResourceLocation;

public class SmeltingRecipeBuilder extends CookingRecipeBuilder {

    public static final ResourceLocation ID = Modopedia.id("smelting_recipe");

    protected SmeltingRecipeBuilder(ResourceLocation recipe) {
        super(ID, recipe);
    }

    protected SmeltingRecipeBuilder(String recipe) {
        super(ID, recipe);
    }

    public static SmeltingRecipeBuilder of(ResourceLocation recipe) {
        return new SmeltingRecipeBuilder(recipe);
    }

    public static SmeltingRecipeBuilder of(String recipe) {
        return new SmeltingRecipeBuilder(recipe);
    }

}
