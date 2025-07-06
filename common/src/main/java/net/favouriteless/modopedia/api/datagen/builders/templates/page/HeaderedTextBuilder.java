package net.favouriteless.modopedia.api.datagen.builders.templates.page;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.common.datagen.builders.TemplateComponentBuilder;
import net.favouriteless.modopedia.book.text.Justify;

import net.minecraft.resources.*;

public class HeaderedTextBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation ID = Modopedia.id("page/headered_text");

    private final String header;
    private final String text;

    private Either<Integer, String> width;
    private Either<Integer, String> lineHeight;
    private Either<Justify, String> justify;

    private Either<Boolean, String> centered;
    private Either<Boolean, String> bold;
    private Either<Boolean, String> underline;
    private Either<Integer, String> colour;

    protected HeaderedTextBuilder(String header, String text) {
        super(ID);
        this.header = header;
        this.text = text;
    }

    public static HeaderedTextBuilder of(String header, String text) {
        return new HeaderedTextBuilder(header, text);
    }

    @Override
    public HeaderedTextBuilder x(int x) {
        return (HeaderedTextBuilder)super.x(x);
    }

    @Override
    public HeaderedTextBuilder x(String x) {
        return (HeaderedTextBuilder)super.x(x);
    }

    @Override
    public HeaderedTextBuilder y(int y) {
        return (HeaderedTextBuilder)super.y(y);
    }

    @Override
    public HeaderedTextBuilder y(String y) {
        return (HeaderedTextBuilder)super.y(y);
    }

    public HeaderedTextBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public HeaderedTextBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    public HeaderedTextBuilder lineHeight(int height) {
        this.lineHeight = Either.left(height);
        return this;
    }

    public HeaderedTextBuilder lineHeight(String height) {
        this.lineHeight = Either.right(height);
        return this;
    }

    public HeaderedTextBuilder justify(Justify justify) {
        this.justify = Either.left(justify);
        return this;
    }

    public HeaderedTextBuilder justify(String justify) {
        this.justify = Either.right(justify);
        return this;
    }

    public HeaderedTextBuilder centered(boolean centered) {
        this.centered = Either.left(centered);
        return this;
    }

    public HeaderedTextBuilder centered(String centered) {
        this.centered = Either.right(centered);
        return this;
    }

    public HeaderedTextBuilder colour(int colour) {
        this.colour = Either.left(colour);
        return this;
    }

    public HeaderedTextBuilder colour(String colour) {
        this.colour = Either.right(colour);
        return this;
    }

    public HeaderedTextBuilder bold(boolean centered) {
        this.bold = Either.left(centered);
        return this;
    }

    public HeaderedTextBuilder bold(String reference) {
        this.bold = Either.right(reference);
        return this;
    }

    public HeaderedTextBuilder underline(boolean underline) {
        this.underline = Either.left(underline);
        return this;
    }

    public HeaderedTextBuilder underline(String underline) {
        this.underline = Either.right(underline);
        return this;
    }

    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {
        json.add("header", resolveString(header));
        json.add("text", resolveString(text));

        if(width != null)
            json.add("width", resolveNum(width));
        if(lineHeight != null)
            json.add("line_height", resolveNum(lineHeight));
        if(justify != null)
            json.add("justify", resolve(justify, j -> Justify.CODEC.encodeStart(ops, j).getOrThrow()));
        if(centered != null)
            json.add("centered", resolveBool(centered));
        if(bold != null)
            json.add("bold", resolveBool(bold));
        if(underline != null)
            json.add("underline", resolveBool(underline));
        if(colour != null)
            json.add("colour", resolveNum(colour));
    }

}
