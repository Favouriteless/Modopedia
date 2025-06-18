package net.favouriteless.modopedia.api.datagen.builders.page_components;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.book.text.Justify;
import net.favouriteless.modopedia.client.page_components.TextPageComponent;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;

public class TextPageComponentBuilder extends PageComponentBuilder {

    private final String text;

    private Either<Integer, String> width;
    private Either<Integer, String> lineHeight;
    private Either<Justify, String> justify;

    private TextPageComponentBuilder(String text) {
        super(TextPageComponent.ID, false);
        this.text = text;
    }

    public static TextPageComponentBuilder of(String text) {
        return new TextPageComponentBuilder(text);
    }

    @Override
    public TextPageComponentBuilder x(int x) {
        return (TextPageComponentBuilder)super.x(x);
    }

    @Override
    public TextPageComponentBuilder x(String reference) {
        return (TextPageComponentBuilder)super.x(reference);
    }

    @Override
    public TextPageComponentBuilder y(int y) {
        return (TextPageComponentBuilder)super.y(y);
    }

    @Override
    public TextPageComponentBuilder y(String reference) {
        return (TextPageComponentBuilder)super.y(reference);
    }

    public TextPageComponentBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public TextPageComponentBuilder width(String reference) {
        this.width = Either.right(reference);
        return this;
    }

    public TextPageComponentBuilder lineHeight(int height) {
        this.lineHeight = Either.left(height);
        return this;
    }

    public TextPageComponentBuilder lineHeight(String reference) {
        this.lineHeight = Either.right(reference);
        return this;
    }

    public TextPageComponentBuilder justify(Justify justify) {
        this.justify = Either.left(justify);
        return this;
    }

    public TextPageComponentBuilder justify(String reference) {
        this.justify = Either.right(reference);
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
            json.add("justify", resolve(justify).orElse(Justify.CODEC.encodeStart(JsonOps.INSTANCE, orThrow(justify)).getOrThrow()));
    }

}
