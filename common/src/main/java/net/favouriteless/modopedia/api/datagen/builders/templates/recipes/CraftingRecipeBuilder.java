package net.favouriteless.modopedia.api.datagen.builders.templates.recipes;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.datagen.builders.SingleRecipeTemplateBuilder;
import net.minecraft.resources.ResourceLocation;

public class CraftingRecipeBuilder {

    public static final ResourceLocation ID = Modopedia.id("recipe/crafting");

    public static SingleRecipeTemplateBuilder of(ResourceLocation recipe) {
        return new SingleRecipeTemplateBuilder(ID, recipe);
    }

    public static SingleRecipeTemplateBuilder of(String recipe) {
        return new SingleRecipeTemplateBuilder(ID, recipe);
    }
    
}
