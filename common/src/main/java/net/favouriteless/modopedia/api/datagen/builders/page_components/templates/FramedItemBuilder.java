package net.favouriteless.modopedia.api.datagen.builders.page_components.templates;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.Modopedia;
import net.minecraft.resources.ResourceLocation;

public class FramedItemBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation ID = Modopedia.id("framed_item");

    private Either<Integer, String> width;

    protected FramedItemBuilder() {
        super(Modopedia.id("framed_item"));
    }

    public static FramedItemBuilder of() {
        return new FramedItemBuilder();
    }

    public FramedItemBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public FramedItemBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    @Override
    protected void build(JsonObject json) {
        if(width != null)
            resolveNum(width).ifPresent(w -> json.add("width", w));
    }

}
