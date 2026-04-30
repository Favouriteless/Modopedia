package net.favouriteless.modopedia.api.datagen.builders.templates;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.datagen.builders.page_components.TemplateComponentBuilder;

import net.minecraft.resources.*;

import java.util.List;

public class FramedImageBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation LARGE_ID = Modopedia.id("large_framed_image");
    public static final ResourceLocation MEDIUM_ID = Modopedia.id("medium_framed_image");

    private final Either<List<ResourceLocation>, String> images;
    private Either<Integer, String> width;

    protected FramedImageBuilder(ResourceLocation id, ResourceLocation... images) {
        super(id);
        this.images = Either.left(List.of(images));
    }

    protected FramedImageBuilder(ResourceLocation id, String images) {
        super(id);
        this.images = Either.right(images);
    }

    public static FramedImageBuilder large(ResourceLocation... images) {
        return new FramedImageBuilder(LARGE_ID, images);
    }

    public static FramedImageBuilder large(String images) {
        return new FramedImageBuilder(LARGE_ID, images);
    }


    public static FramedImageBuilder medium(ResourceLocation... images) {
        return new FramedImageBuilder(MEDIUM_ID, images);
    }

    public static FramedImageBuilder medium(String images) {
        return new FramedImageBuilder(MEDIUM_ID, images);
    }

    @Override
    public FramedImageBuilder x(int x) {
        return (FramedImageBuilder)super.x(x);
    }

    @Override
    public FramedImageBuilder x(String x) {
        return (FramedImageBuilder)super.x(x);
    }

    @Override
    public FramedImageBuilder y(int y) {
        return (FramedImageBuilder)super.y(y);
    }

    @Override
    public FramedImageBuilder y(String y) {
        return (FramedImageBuilder)super.y(y);
    }

    public FramedImageBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public FramedImageBuilder width(String width) {
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
