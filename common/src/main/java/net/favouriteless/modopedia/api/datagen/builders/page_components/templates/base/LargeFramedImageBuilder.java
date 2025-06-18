package net.favouriteless.modopedia.api.datagen.builders.page_components.templates.base;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.datagen.TemplateComponentBuilder;
import net.minecraft.resources.ResourceLocation;

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
    protected void build(JsonObject json) {
        json.add("images", resolve(images).orElseGet(() -> ResourceLocation.CODEC.listOf().encodeStart(JsonOps.INSTANCE, orThrow(images)).getOrThrow()));
        if(width != null)
            resolveNum(width).ifPresent(w -> json.add("width", w));
    }

}
