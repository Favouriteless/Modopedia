package net.favouriteless.modopedia.api.datagen.builders.templates;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.common.datagen.builders.TemplateComponentBuilder;

import net.minecraft.resources.*;

import java.util.List;

public class LargeFramedImageBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation ID = Modopedia.id("large_framed_image");

    private final Either<List<ResourceLocation>, String> images;
    private Either<Integer, String> width;

    protected LargeFramedImageBuilder(ResourceLocation... images) {
        super(ID);
        this.images = Either.left(List.of(images));
    }

    protected LargeFramedImageBuilder(String images) {
        super(ID);
        this.images = Either.right(images);
    }

    public static LargeFramedImageBuilder of(ResourceLocation... images) {
        return new LargeFramedImageBuilder(images);
    }

    public static LargeFramedImageBuilder of(String images) {
        return new LargeFramedImageBuilder(images);
    }

    @Override
    public LargeFramedImageBuilder x(int x) {
        return (LargeFramedImageBuilder)super.x(x);
    }

    @Override
    public LargeFramedImageBuilder x(String x) {
        return (LargeFramedImageBuilder)super.x(x);
    }

    @Override
    public LargeFramedImageBuilder y(int y) {
        return (LargeFramedImageBuilder)super.y(y);
    }

    @Override
    public LargeFramedImageBuilder y(String y) {
        return (LargeFramedImageBuilder)super.y(y);
    }

    public LargeFramedImageBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public LargeFramedImageBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {
        json.add("images", resolve(images, l -> ResourceLocation.CODEC.listOf().encodeStart(ops, l).getOrThrow()));

        if(width != null)
            json.add("width", resolveNum(width));
    }

}
