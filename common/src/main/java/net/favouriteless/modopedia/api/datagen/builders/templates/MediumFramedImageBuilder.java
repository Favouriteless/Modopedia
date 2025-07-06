package net.favouriteless.modopedia.api.datagen.builders.templates;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.common.datagen.builders.TemplateComponentBuilder;

import net.minecraft.resources.*;

import java.util.List;

public class MediumFramedImageBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation ID = Modopedia.id("medium_framed_image");

    private final Either<List<ResourceLocation>, String> images;
    private Either<Integer, String> width;

    protected MediumFramedImageBuilder(ResourceLocation... images) {
        super(ID);
        this.images = Either.left(List.of(images));
    }

    protected MediumFramedImageBuilder(String images) {
        super(ID);
        this.images = Either.right(images);
    }

    public static MediumFramedImageBuilder of(ResourceLocation... images) {
        return new MediumFramedImageBuilder(images);
    }

    public static MediumFramedImageBuilder of(String images) {
        return new MediumFramedImageBuilder(images);
    }

    @Override
    public MediumFramedImageBuilder x(int x) {
        return (MediumFramedImageBuilder)super.x(x);
    }

    @Override
    public MediumFramedImageBuilder x(String x) {
        return (MediumFramedImageBuilder)super.x(x);
    }

    @Override
    public MediumFramedImageBuilder y(int y) {
        return (MediumFramedImageBuilder)super.y(y);
    }

    @Override
    public MediumFramedImageBuilder y(String y) {
        return (MediumFramedImageBuilder)super.y(y);
    }

    public MediumFramedImageBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public MediumFramedImageBuilder width(String width) {
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
