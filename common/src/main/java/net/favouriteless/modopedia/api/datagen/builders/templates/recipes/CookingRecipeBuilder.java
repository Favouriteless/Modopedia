package net.favouriteless.modopedia.api.datagen.builders.templates.recipes;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.datagen.builders.TemplateComponentBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class CookingRecipeBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation ID = Modopedia.id("recipe/cooking");

    private final Either<ResourceLocation, String> recipe;
    private final Either<List<String>, String> tooltip;

    public CookingRecipeBuilder(ResourceLocation id, ResourceLocation recipe, String... tooltip) {
        super(id);
        this.recipe = Either.left(recipe);
        this.tooltip = Either.left(List.of(tooltip));
    }

    public CookingRecipeBuilder(ResourceLocation id, String recipe, String... tooltip) {
        super(id);
        this.recipe = Either.right(recipe);
        this.tooltip = Either.left(List.of(tooltip));
    }

    public CookingRecipeBuilder(ResourceLocation id, ResourceLocation recipe, String tooltip) {
        super(id);
        this.recipe = Either.left(recipe);
        this.tooltip = Either.right(tooltip);
    }

    public CookingRecipeBuilder(ResourceLocation id, String recipe, String tooltip) {
        super(id);
        this.recipe = Either.right(recipe);
        this.tooltip = Either.right(tooltip);
    }

    public static CookingRecipeBuilder of(ResourceLocation recipe, String... tooltip) {
        return new CookingRecipeBuilder(ID, recipe, tooltip);
    }

    public static CookingRecipeBuilder of(String recipe, String... tooltip) {
        return new CookingRecipeBuilder(ID, recipe, tooltip);
    }

    public static CookingRecipeBuilder of(ResourceLocation recipe, String tooltip) {
        return new CookingRecipeBuilder(ID, recipe, tooltip);
    }

    public static CookingRecipeBuilder of(String recipe, String tooltip) {
        return new CookingRecipeBuilder(ID, recipe, tooltip);
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

    @Override
    protected void build(JsonObject json) {
        json.add("recipe", resolve(recipe).orElseGet(() -> ResourceLocation.CODEC.encodeStart(JsonOps.INSTANCE, orThrow(recipe)).getOrThrow()));
        json.add("tooltip", resolve(tooltip).orElseGet(() -> Codec.STRING.listOf().encodeStart(JsonOps.INSTANCE, orThrow(tooltip)).getOrThrow()));
    }

}
