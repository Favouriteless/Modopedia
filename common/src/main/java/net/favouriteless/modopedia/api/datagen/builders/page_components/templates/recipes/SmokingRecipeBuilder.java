package net.favouriteless.modopedia.api.datagen.builders.page_components.templates.recipes;

import net.favouriteless.modopedia.Modopedia;
import net.minecraft.resources.ResourceLocation;

public class SmokingRecipeBuilder extends CookingRecipeBuilder {

    public static final ResourceLocation ID = Modopedia.id("smoking_recipe");

    protected SmokingRecipeBuilder(ResourceLocation recipe) {
        super(ID, recipe);
    }

    protected SmokingRecipeBuilder(String recipe) {
        super(ID, recipe);
    }

    public static SmokingRecipeBuilder of(ResourceLocation recipe) {
        return new SmokingRecipeBuilder(recipe);
    }

    public static SmokingRecipeBuilder of(String recipe) {
        return new SmokingRecipeBuilder(recipe);
    }
    
}
