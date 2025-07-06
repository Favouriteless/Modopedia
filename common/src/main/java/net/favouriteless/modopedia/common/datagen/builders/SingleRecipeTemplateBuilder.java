package net.favouriteless.modopedia.common.datagen.builders;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;

import net.minecraft.resources.*;

public class SingleRecipeTemplateBuilder extends TemplateComponentBuilder {

    private final Either<ResourceLocation, String> recipe;

    public SingleRecipeTemplateBuilder(ResourceLocation id, ResourceLocation recipe) {
        super(id);
        this.recipe = Either.left(recipe);
    }

    public SingleRecipeTemplateBuilder(ResourceLocation id, String recipe) {
        super(id);
        this.recipe = Either.right(recipe);
    }

    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {
        json.add("recipe", resolve(recipe).orElseGet(() -> ResourceLocation.CODEC.encodeStart(ops, orThrow(recipe)).getOrThrow()));
    }

}
