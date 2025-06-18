package net.favouriteless.modopedia.api.datagen.builders.page_components.templates.recipes;

import net.favouriteless.modopedia.Modopedia;
import net.minecraft.resources.ResourceLocation;

public class BlastingRecipeBuilder extends CookingRecipeBuilder {

    public static final ResourceLocation ID = Modopedia.id("blasting_recipe");

    protected BlastingRecipeBuilder(ResourceLocation recipe) {
        super(ID, recipe);
    }

    protected BlastingRecipeBuilder(String recipe) {
        super(ID, recipe);
    }

    public static BlastingRecipeBuilder of(ResourceLocation recipe) {
        return new BlastingRecipeBuilder(recipe);
    }

    public static BlastingRecipeBuilder of(String recipe) {
        return new BlastingRecipeBuilder(recipe);
    }
    
}
