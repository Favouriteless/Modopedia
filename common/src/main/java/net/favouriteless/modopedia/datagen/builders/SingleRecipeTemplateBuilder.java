package net.favouriteless.modopedia.datagen.builders;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;

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
    protected void build(JsonObject json) {
        json.add("recipe", resolve(recipe).orElseGet(() -> ResourceLocation.CODEC.encodeStart(JsonOps.INSTANCE, orThrow(recipe)).getOrThrow()));
    }

}
