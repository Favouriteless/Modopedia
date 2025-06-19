package net.favouriteless.modopedia.api.datagen.builders.page_components.templates.recipes;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.datagen.TemplateComponentBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class CookingRecipeBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation ID = Modopedia.id("cooking_recipe");

    private final Either<ResourceLocation, String> recipe;
    private Either<List<String>, String> tooltip;

    protected CookingRecipeBuilder(ResourceLocation id, ResourceLocation recipe) {
        super(id);
        this.recipe = Either.left(recipe);
    }

    protected CookingRecipeBuilder(ResourceLocation id, String recipe) {
        super(id);
        this.recipe = Either.right(recipe);
    }

    public static CookingRecipeBuilder of(ResourceLocation recipe) {
        return new CookingRecipeBuilder(ID, recipe);
    }

    public static CookingRecipeBuilder of(String recipe) {
        return new CookingRecipeBuilder(ID, recipe);
    }

    @Override
    public CookingRecipeBuilder x(int x) {
        return (CookingRecipeBuilder)super.x(x);
    }

    @Override
    public CookingRecipeBuilder x(String x) {
        return (CookingRecipeBuilder)super.x(x);
    }

    @Override
    public CookingRecipeBuilder y(int y) {
        return (CookingRecipeBuilder)super.y(y);
    }

    @Override
    public CookingRecipeBuilder y(String y) {
        return (CookingRecipeBuilder)super.y(y);
    }

    public CookingRecipeBuilder tooltip(String... tooltip) {
        this.tooltip = Either.left(List.of(tooltip));
        return this;
    }

    public CookingRecipeBuilder tooltip(String tooltip) {
        this.tooltip = Either.right(tooltip);
        return this;
    }

    @Override
    protected void build(JsonObject json) {
        if(tooltip == null)
            throw new IllegalStateException("CookingRecipeBuilder has no tooltip");

        json.add("recipe", resolve(recipe).orElseGet(() -> ResourceLocation.CODEC.encodeStart(JsonOps.INSTANCE, orThrow(recipe)).getOrThrow()));
        json.add("tooltip", resolve(tooltip).orElseGet(() -> Codec.STRING.listOf().encodeStart(JsonOps.INSTANCE, orThrow(tooltip)).getOrThrow()));
    }

}
