package net.favouriteless.modopedia.api.datagen.builders.page_components.components;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;

import net.minecraft.resources.RegistryOps;

import java.util.List;

public class GalleryBuilder extends PageComponentBuilder {

    private final List<PageComponentBuilder> components;
    private Either<Integer, String> width;
    private Either<Integer, String> height;

    private GalleryBuilder(PageComponentBuilder... components) {
        super(null);
        this.components = List.of(components);
    }

    public static GalleryBuilder of(PageComponentBuilder... components) {
        return new GalleryBuilder(components);
    }

    @Override
    public GalleryBuilder x(int x) {
        return (GalleryBuilder)super.x(x);
    }

    @Override
    public GalleryBuilder x(String x) {
        return (GalleryBuilder)super.x(x);
    }

    @Override
    public GalleryBuilder y(int y) {
        return (GalleryBuilder)super.y(y);
    }

    @Override
    public GalleryBuilder y(String y) {
        return (GalleryBuilder)super.y(y);
    }

    public GalleryBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public GalleryBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    public GalleryBuilder height(int height) {
        this.height = Either.left(height);
        return this;
    }

    public GalleryBuilder height(String height) {
        this.height = Either.right(height);
        return this;
    }

    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {
        JsonArray components = new JsonArray();
        for(PageComponentBuilder builder : this.components) {
            components.add(builder.build(ops));
        }
        json.add("components", components);

        if(width != null)
            json.add("width", resolveNum(width));
        if(height != null)
            json.add("height", resolveNum(height));
    }

}
