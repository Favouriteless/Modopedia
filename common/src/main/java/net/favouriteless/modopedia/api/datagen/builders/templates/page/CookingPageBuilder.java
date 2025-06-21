package net.favouriteless.modopedia.api.datagen.builders.templates.page;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.datagen.builders.DoubleRecipeTemplateBuilder;
import net.favouriteless.modopedia.datagen.builders.SingleRecipeTemplateBuilder;
import net.minecraft.resources.ResourceLocation;

public class CookingPageBuilder {

    public static final ResourceLocation ID = Modopedia.id("page/cooking");

    public static SingleRecipeTemplateBuilder of(ResourceLocation recipe) {
        return new SingleRecipeTemplateBuilder(ID, recipe);
    }

    public static SingleRecipeTemplateBuilder of(String recipe) {
        return new SingleRecipeTemplateBuilder(ID, recipe);
    }
    
}