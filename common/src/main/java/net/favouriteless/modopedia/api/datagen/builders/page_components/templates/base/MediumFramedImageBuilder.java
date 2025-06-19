package net.favouriteless.modopedia.api.datagen.builders.page_components.templates.base;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.datagen.TemplateComponentBuilder;
import net.minecraft.resources.ResourceLocation;

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
    protected void build(JsonObject json) {
        json.add("images", resolve(images).orElseGet(() -> ResourceLocation.CODEC.listOf().encodeStart(JsonOps.INSTANCE, orThrow(images)).getOrThrow()));
        if(width != null)
            resolveNum(width).ifPresent(w -> json.add("width", w));
    }

}
