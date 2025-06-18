package net.favouriteless.modopedia.api.datagen.builders.page_components;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.api.multiblock.Multiblock;
import net.favouriteless.modopedia.client.page_components.MultiblockPageComponent;
import net.minecraft.resources.ResourceLocation;

public class MultiblockPageComponentBuilder extends PageComponentBuilder {

    private Either<Multiblock, String> multiblock;
    private Either<ResourceLocation, String> multiblockId;

    private Either<Integer, String> width;
    private Either<Integer, String> height;

    private Either<Float, String> offsetX;
    private Either<Float, String> offsetY;

    private Either<Float, String> scale;
    private Either<Float, String> viewAngle;
    private Either<Boolean, String> noOffsets;

    private MultiblockPageComponentBuilder() {
        super(MultiblockPageComponent.ID);
    }

    private MultiblockPageComponentBuilder(Multiblock multiblock) {
        this();
        this.multiblock = Either.left(multiblock);
    }

    private MultiblockPageComponentBuilder(String multiblock) {
        this();
        this.multiblock = Either.right(multiblock);
    }

    public static MultiblockPageComponentBuilder of(Multiblock multiblock) {
        return new MultiblockPageComponentBuilder(multiblock);
    }

    public static MultiblockPageComponentBuilder of(String multiblock) {
        return new MultiblockPageComponentBuilder(multiblock);
    }

    public static MultiblockPageComponentBuilder ofId(ResourceLocation multiblock) {
        return new MultiblockPageComponentBuilder().multiblockId(multiblock);
    }

    public static MultiblockPageComponentBuilder ofId(String multiblock) {
        return new MultiblockPageComponentBuilder().multiblockId(multiblock);
    }

    public MultiblockPageComponentBuilder multiblockId(ResourceLocation multiblock) {
        this.multiblockId = Either.left(multiblock);
        return this;
    }

    public MultiblockPageComponentBuilder multiblockId(String multiblock) {
        this.multiblockId = Either.right(multiblock);
        return this;
    }

    @Override
    public MultiblockPageComponentBuilder x(int x) {
        return (MultiblockPageComponentBuilder)super.x(x);
    }

    @Override
    public MultiblockPageComponentBuilder x(String reference) {
        return (MultiblockPageComponentBuilder)super.x(reference);
    }

    @Override
    public MultiblockPageComponentBuilder y(int y) {
        return (MultiblockPageComponentBuilder)super.y(y);
    }

    @Override
    public MultiblockPageComponentBuilder y(String reference) {
        return (MultiblockPageComponentBuilder)super.y(reference);
    }

    public MultiblockPageComponentBuilder scale(float scale) {
        this.scale = Either.left(scale);
        return this;
    }

    public MultiblockPageComponentBuilder scale(String scale) {
        this.scale = Either.right(scale);
        return this;
    }

    public MultiblockPageComponentBuilder offsetX(float offsetX) {
        this.offsetX = Either.left(offsetX);
        return this;
    }

    public MultiblockPageComponentBuilder offsetX(String offsetX) {
        this.offsetX = Either.right(offsetX);
        return this;
    }

    public MultiblockPageComponentBuilder offsetY(float offsetY) {
        this.offsetY = Either.left(offsetY);
        return this;
    }

    public MultiblockPageComponentBuilder offsetY(String offsetY) {
        this.offsetY = Either.right(offsetY);
        return this;
    }

    public MultiblockPageComponentBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public MultiblockPageComponentBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    public MultiblockPageComponentBuilder height(int height) {
        this.height = Either.left(height);
        return this;
    }

    public MultiblockPageComponentBuilder height(String height) {
        this.height = Either.right(height);
        return this;
    }

    public MultiblockPageComponentBuilder viewAngle(float viewAngle) {
        this.viewAngle = Either.left(viewAngle);
        return this;
    }

    public MultiblockPageComponentBuilder viewAngle(String viewAngle) {
        this.viewAngle = Either.right(viewAngle);
        return this;
    }

    public MultiblockPageComponentBuilder noOffsets(boolean noOffsets) {
        this.noOffsets = Either.left(noOffsets);
        return this;
    }

    public MultiblockPageComponentBuilder noOffsets(String noOffsets) {
        this.noOffsets = Either.right(noOffsets);
        return this;
    }

    @Override
    protected void build(JsonObject json) {
        if(multiblock != null)
            json.add("multiblock", resolve(multiblock).orElse(Multiblock.codec().encodeStart(JsonOps.INSTANCE, multiblock.left().orElseThrow()).getOrThrow()));
        if(multiblockId != null)
            json.add("multiblock_id", resolve(multiblockId).orElse(ResourceLocation.CODEC.encodeStart(JsonOps.INSTANCE, multiblockId.left().orElseThrow()).getOrThrow()));
        if(width != null)
            resolveNum(width).ifPresent(w -> json.add("width", w));
        if(height != null)
            resolveNum(height).ifPresent(h -> json.add("height", h));
        if(offsetX != null)
            resolveNum(offsetX).ifPresent(o -> json.add("offset_x", o));
        if(offsetY != null)
            resolveNum(offsetY).ifPresent(o -> json.add("offset_y", o));
        if(scale != null)
            resolveNum(scale).ifPresent(s -> json.add("scale", s));
        if(viewAngle != null)
            resolveNum(viewAngle).ifPresent(v -> json.add("view_angle", v));
        if(noOffsets != null)
            resolveBool(noOffsets).ifPresent(b -> json.add("no_offsets", b));
    }

}
