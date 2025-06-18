package net.favouriteless.modopedia.api.datagen.builders.page_components;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.book.text.Justify;
import net.favouriteless.modopedia.client.page_components.TextPageComponent;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;

public class TextComponentBuilder extends PageComponentBuilder {

    private final String text;

    private Either<Integer, String> width;
    private Either<Integer, String> lineHeight;
    private Either<Justify, String> justify;

    private TextComponentBuilder(String text) {
        super(TextPageComponent.ID, false);
        this.text = text;
    }

    public static TextComponentBuilder of(String text) {
        return new TextComponentBuilder(text);
    }

    @Override
    public TextComponentBuilder x(int x) {
        return (TextComponentBuilder)super.x(x);
    }

    @Override
    public TextComponentBuilder x(String x) {
        return (TextComponentBuilder)super.x(x);
    }

    @Override
    public TextComponentBuilder y(int y) {
        return (TextComponentBuilder)super.y(y);
    }

    @Override
    public TextComponentBuilder y(String y) {
        return (TextComponentBuilder)super.y(y);
    }

    public TextComponentBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public TextComponentBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    public TextComponentBuilder lineHeight(int height) {
        this.lineHeight = Either.left(height);
        return this;
    }

    public TextComponentBuilder lineHeight(String height) {
        this.lineHeight = Either.right(height);
        return this;
    }

    public TextComponentBuilder justify(Justify justify) {
        this.justify = Either.left(justify);
        return this;
    }

    public TextComponentBuilder justify(String justify) {
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
