package net.favouriteless.modopedia.api.datagen.builders.templates.page;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.common.datagen.builders.DoubleRecipeTemplateBuilder;
import net.minecraft.resources.ResourceLocation;

public class DoubleCraftingPageBuilder {

    public static final ResourceLocation ID = Modopedia.id("page/double_crafting");

    public static DoubleRecipeTemplateBuilder of(ResourceLocation recipe1, ResourceLocation recipe2) {
        return new DoubleRecipeTemplateBuilder(ID, recipe1, recipe2);
    }

    public static DoubleRecipeTemplateBuilder of(ResourceLocation recipe1, String recipe2) {
        return new DoubleRecipeTemplateBuilder(ID, recipe1, recipe2);
    }

    public static DoubleRecipeTemplateBuilder of(String recipe1, ResourceLocation recipe2) {
        return new DoubleRecipeTemplateBuilder(ID, recipe1, recipe2);
    }

    public static DoubleRecipeTemplateBuilder of(String recipe1, String recipe2) {
        return new DoubleRecipeTemplateBuilder(ID, recipe1, recipe2);
    }
    
}