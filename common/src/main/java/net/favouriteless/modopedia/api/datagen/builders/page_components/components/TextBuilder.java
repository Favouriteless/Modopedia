package net.favouriteless.modopedia.api.datagen.builders.page_components.components;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.book.text.Justify;
import net.favouriteless.modopedia.client.page_components.TextPageComponent;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;

public class TextBuilder extends PageComponentBuilder {

    private final String text;

    private Either<Integer, String> width;
    private Either<Integer, String> lineHeight;
    private Either<Justify, String> justify;

    private TextBuilder(String text) {
        super(TextPageComponent.ID, false);
        this.text = text;
    }

    public static TextBuilder of(String text) {
        return new TextBuilder(text);
    }

    @Override
    public TextBuilder x(int x) {
        return (TextBuilder)super.x(x);
    }

    @Override
    public TextBuilder x(String x) {
        return (TextBuilder)super.x(x);
    }

    @Override
    public TextBuilder y(int y) {
        return (TextBuilder)super.y(y);
    }

    @Override
    public TextBuilder y(String y) {
        return (TextBuilder)super.y(y);
    }

    public TextBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public TextBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    public TextBuilder lineHeight(int height) {
        this.lineHeight = Either.left(height);
        return this;
    }

    public TextBuilder lineHeight(String height) {
        this.lineHeight = Either.right(height);
        return this;
    }

    public TextBuilder justify(Justify justify) {
        this.justify = Either.left(justify);
        return this;
    }

    public TextBuilder justify(String justify) {
        this.justify = Either.right(justify);
        return this;
    }

    @Override
    protected void build(JsonObject json) {
        json.add("text", new JsonPrimitive(text));

        if(width != null)
            resolveNum(width).ifPresent(j -> json.add("width", j));
        if(lineHeight != null)
            resolveNum(lineHeight).ifPresent(j -> json.add("line_height", j));
        if(justify != null)
            json.add("justify", resolve(justify).orElseGet(() -> Justify.CODEC.encodeStart(JsonOps.INSTANCE, orThrow(justify)).getOrThrow()));
    }

}
