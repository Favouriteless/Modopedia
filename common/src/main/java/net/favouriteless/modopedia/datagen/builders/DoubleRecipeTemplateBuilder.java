package net.favouriteless.modopedia.datagen.builders;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;

public class DoubleRecipeTemplateBuilder extends TemplateComponentBuilder {

    private final Either<ResourceLocation, String> recipe1;
    private final Either<ResourceLocation, String> recipe2;

    public DoubleRecipeTemplateBuilder(ResourceLocation id, ResourceLocation recipe1, ResourceLocation recipe2) {
        super(id);
        this.recipe1 = Either.left(recipe1);
        this.recipe2 = Either.left(recipe2);
    }

    public DoubleRecipeTemplateBuilder(ResourceLocation id, ResourceLocation recipe1, String recipe2) {
        super(id);
        this.recipe1 = Either.left(recipe1);
        this.recipe2 = Either.right(recipe2);
    }

    public DoubleRecipeTemplateBuilder(ResourceLocation id, String recipe1, ResourceLocation recipe2) {
        super(id);
        this.recipe1 = Either.right(recipe1);
        this.recipe2 = Either.left(recipe2);
    }

    public DoubleRecipeTemplateBuilder(ResourceLocation id, String recipe1, String recipe2) {
        super(id);
        this.recipe1 = Either.right(recipe1);
        this.recipe2 = Either.right(recipe2);
    }

    @Override
    protected void build(JsonObject json) {
        json.add("recipe1", resolve(recipe1).orElseGet(() -> ResourceLocation.CODEC.encodeStart(JsonOps.INSTANCE, orThrow(recipe1)).getOrThrow()));
        json.add("recipe2", resolve(recipe2).orElseGet(() -> ResourceLocation.CODEC.encodeStart(JsonOps.INSTANCE, orThrow(recipe2)).getOrThrow()));
    }

}
