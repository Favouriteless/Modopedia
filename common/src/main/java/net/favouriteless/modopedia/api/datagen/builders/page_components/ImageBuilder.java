package net.favouriteless.modopedia.api.datagen.builders.page_components;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.ImagePageComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.List;

public class ImageBuilder extends PageComponentBuilder {

    protected Either<List<ResourceLocation>, String> images;
    private Either<Integer, String> width;
    private Either<Integer, String> height;

    private ImageBuilder(String images) {
        super(ImagePageComponent.ID);
        this.images = Either.right(images);
    }

    private ImageBuilder(ResourceLocation... images) {
        super(ImagePageComponent.ID);
        this.images = Either.left(Arrays.asList(images));
    }

    public static ImageBuilder of(ResourceLocation... images) {
        return new ImageBuilder(images);
    }

    public static ImageBuilder of(String images) {
        return new ImageBuilder(images);
    }

    @Override
    public ImageBuilder x(int x) {
        return (ImageBuilder)super.x(x);
    }

    @Override
    public ImageBuilder x(String x) {
        return (ImageBuilder)super.x(x);
    }

    @Override
    public ImageBuilder y(int y) {
        return (ImageBuilder)super.y(y);
    }

    @Override
    public ImageBuilder y(String y) {
        return (ImageBuilder)super.y(y);
    }

    public ImageBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public ImageBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    public ImageBuilder height(int height) {
        this.height = Either.left(height);
        return this;
    }

    public ImageBuilder height(String height) {
        this.height = Either.right(height);
        return this;
    }

    @Override
    protected void build(JsonObject json) {
        json.add("images", resolve(images).orElseGet(() -> ResourceLocation.CODEC.listOf().encodeStart(JsonOps.INSTANCE, orThrow(images)).getOrThrow()));

        if(width != null)
            resolveNum(width).ifPresent(w -> json.add("width", w));
        if(height != null)
            resolveNum(height).ifPresent(h -> json.add("height", h));
    }

}
