package net.favouriteless.modopedia.api.datagen.builders.page_components;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.book.text.Justify;
import net.favouriteless.modopedia.client.page_components.ImagePageComponent;
import net.favouriteless.modopedia.client.page_components.TextPageComponent;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ImagePageComponentBuilder extends PageComponentBuilder {

    protected Either<List<ResourceLocation>, String> images;
    private Either<Integer, String> width;
    private Either<Integer, String> height;

    private ImagePageComponentBuilder(String images) {
        super(ImagePageComponent.ID);
        this.images = Either.right(images);
    }

    private ImagePageComponentBuilder(ResourceLocation... images) {
        super(ImagePageComponent.ID);
        this.images = Either.left(Arrays.asList(images));
    }

    public static ImagePageComponentBuilder of(ResourceLocation... images) {
        return new ImagePageComponentBuilder(images);
    }

    public static ImagePageComponentBuilder of(String images) {
        return new ImagePageComponentBuilder(images);
    }

    @Override
    public ImagePageComponentBuilder x(int x) {
        return (ImagePageComponentBuilder)super.x(x);
    }

    @Override
    public ImagePageComponentBuilder x(String x) {
        return (ImagePageComponentBuilder)super.x(x);
    }

    @Override
    public ImagePageComponentBuilder y(int y) {
        return (ImagePageComponentBuilder)super.y(y);
    }

    @Override
    public ImagePageComponentBuilder y(String y) {
        return (ImagePageComponentBuilder)super.y(y);
    }

    public ImagePageComponentBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public ImagePageComponentBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    public ImagePageComponentBuilder height(int height) {
        this.height = Either.left(height);
        return this;
    }

    public ImagePageComponentBuilder height(String height) {
        this.height = Either.right(height);
        return this;
    }

    @Override
    protected void build(JsonObject json) {
        json.add("images", resolve(images).orElse(ResourceLocation.CODEC.listOf().encodeStart(JsonOps.INSTANCE, orThrow(images)).getOrThrow()));

        if(width != null)
            resolveNum(width).ifPresent(w -> json.add("width", w));
        if(height != null)
            resolveNum(height).ifPresent(h -> json.add("height", h));
    }

}
