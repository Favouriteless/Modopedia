package net.favouriteless.modopedia.api.datagen.builders.page_components.components;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.TooltipPageComponent;

import net.minecraft.resources.RegistryOps;

import java.util.List;

public class TooltipBuilder extends PageComponentBuilder {

    protected final Either<List<String>, String> tooltipLines;
    protected Either<Integer, String> width;
    protected Either<Integer, String> height;

    private TooltipBuilder(String... tooltipLines) {
        super(TooltipPageComponent.ID, false);
        this.tooltipLines = Either.left(List.of(tooltipLines));
    }

    private TooltipBuilder(String tooltipLines) {
        super(TooltipPageComponent.ID, false);
        this.tooltipLines = Either.right(tooltipLines);
    }

    public static TooltipBuilder of(String... tooltipLines) {
        return new TooltipBuilder(tooltipLines);
    }

    public static TooltipBuilder of(String tooltipLines) {
        return new TooltipBuilder(tooltipLines);
    }

    @Override
    public TooltipBuilder x(int x) {
        return (TooltipBuilder)super.x(x);
    }

    @Override
    public TooltipBuilder x(String x) {
        return (TooltipBuilder)super.x(x);
    }

    @Override
    public TooltipBuilder y(int y) {
        return (TooltipBuilder)super.y(y);
    }

    @Override
    public TooltipBuilder y(String y) {
        return (TooltipBuilder)super.y(y);
    }

    public TooltipBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public TooltipBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    public TooltipBuilder height(int height) {
        this.height = Either.left(height);
        return this;
    }

    public TooltipBuilder height(String height) {
        this.height = Either.right(height);
        return this;
    }


    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {
        json.add("tooltip", resolve(tooltipLines, l -> Codec.STRING.listOf().encodeStart(ops, l).getOrThrow()));

        if(width != null)
            json.add("width", resolveNum(width));
        if(height != null)
            json.add("height", resolveNum(height));
    }

}
