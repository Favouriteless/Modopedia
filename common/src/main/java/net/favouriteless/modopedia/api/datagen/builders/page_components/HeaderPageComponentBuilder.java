package net.favouriteless.modopedia.api.datagen.builders.page_components;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.book.text.Justify;
import net.favouriteless.modopedia.client.page_components.HeaderPageComponent;
import net.favouriteless.modopedia.client.page_components.TextPageComponent;

public class HeaderPageComponentBuilder extends PageComponentBuilder {

    private final String text;

    private Either<Boolean, String> centered;
    private Either<Boolean, String> bold;
    private Either<Boolean, String> underline;
    private Either<Integer, String> colour;

    private HeaderPageComponentBuilder(String text) {
        super(HeaderPageComponent.ID);
        this.text = text;
    }

    public static HeaderPageComponentBuilder of(String text) {
        return new HeaderPageComponentBuilder(text);
    }

    @Override
    public HeaderPageComponentBuilder x(int x) {
        return (HeaderPageComponentBuilder)super.x(x);
    }

    @Override
    public HeaderPageComponentBuilder x(String x) {
        return (HeaderPageComponentBuilder)super.x(x);
    }

    @Override
    public HeaderPageComponentBuilder y(int y) {
        return (HeaderPageComponentBuilder)super.y(y);
    }

    @Override
    public HeaderPageComponentBuilder y(String y) {
        return (HeaderPageComponentBuilder)super.y(y);
    }

    public HeaderPageComponentBuilder centered(boolean centered) {
        this.centered = Either.left(centered);
        return this;
    }

    public HeaderPageComponentBuilder centered(String centered) {
        this.centered = Either.right(centered);
        return this;
    }

    public HeaderPageComponentBuilder colour(int colour) {
        this.colour = Either.left(colour);
        return this;
    }

    public HeaderPageComponentBuilder colour(String colour) {
        this.colour = Either.right(colour);
        return this;
    }

    public HeaderPageComponentBuilder bold(boolean centered) {
        this.bold = Either.left(centered);
        return this;
    }

    public HeaderPageComponentBuilder bold(String reference) {
        this.bold = Either.right(reference);
        return this;
    }

    public HeaderPageComponentBuilder underline(boolean underline) {
        this.underline = Either.left(underline);
        return this;
    }

    public HeaderPageComponentBuilder underline(String underline) {
        this.underline = Either.right(underline);
        return this;
    }

    @Override
    protected void build(JsonObject json) {
        json.add("text", new JsonPrimitive(text));

        if(centered != null)
            resolveBool(centered).ifPresent(c -> json.add("centered", c));
        if(bold != null)
            resolveBool(bold).ifPresent(b -> json.add("bold", b));
        if(underline != null)
            resolveBool(underline).ifPresent(u -> json.add("underline", u));
        if(colour != null)
            resolveNum(colour).ifPresent(c -> json.add("colour", c));
    }

}
