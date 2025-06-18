package net.favouriteless.modopedia.api.datagen.builders.page_components;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.api.multiblock.Multiblock;
import net.favouriteless.modopedia.client.page_components.MultiblockPageComponent;
import net.minecraft.resources.ResourceLocation;

public class MultiblockBuilder extends PageComponentBuilder {

    private Either<Multiblock, String> multiblock;
    private Either<ResourceLocation, String> multiblockId;

    private Either<Integer, String> width;
    private Either<Integer, String> height;

    private Either<Float, String> offsetX;
    private Either<Float, String> offsetY;

    private Either<Float, String> scale;
    private Either<Float, String> viewAngle;
    private Either<Boolean, String> noOffsets;

    private MultiblockBuilder() {
        super(MultiblockPageComponent.ID);
    }

    private MultiblockBuilder(Multiblock multiblock) {
        this();
        this.multiblock = Either.left(multiblock);
    }

    private MultiblockBuilder(String multiblock) {
        this();
        this.multiblock = Either.right(multiblock);
    }

    public static MultiblockBuilder of(Multiblock multiblock) {
        return new MultiblockBuilder(multiblock);
    }

    public static MultiblockBuilder of(String multiblock) {
        return new MultiblockBuilder(multiblock);
    }

    public static MultiblockBuilder ofId(ResourceLocation multiblock) {
        return new MultiblockBuilder().multiblockId(multiblock);
    }

    public static MultiblockBuilder ofId(String multiblock) {
        return new MultiblockBuilder().multiblockId(multiblock);
    }

    public MultiblockBuilder multiblockId(ResourceLocation multiblock) {
        this.multiblockId = Either.left(multiblock);
        return this;
    }

    public MultiblockBuilder multiblockId(String multiblock) {
        this.multiblockId = Either.right(multiblock);
        return this;
    }

    @Override
    public MultiblockBuilder x(int x) {
        return (MultiblockBuilder)super.x(x);
    }

    @Override
    public MultiblockBuilder x(String x) {
        return (MultiblockBuilder)super.x(x);
    }

    @Override
    public MultiblockBuilder y(int y) {
        return (MultiblockBuilder)super.y(y);
    }

    @Override
    public MultiblockBuilder y(String y) {
        return (MultiblockBuilder)super.y(y);
    }

    public MultiblockBuilder scale(float scale) {
        this.scale = Either.left(scale);
        return this;
    }

    public MultiblockBuilder scale(String scale) {
        this.scale = Either.right(scale);
        return this;
    }

    public MultiblockBuilder offsetX(float offsetX) {
        this.offsetX = Either.left(offsetX);
        return this;
    }

    public MultiblockBuilder offsetX(String offsetX) {
        this.offsetX = Either.right(offsetX);
        return this;
    }

    public MultiblockBuilder offsetY(float offsetY) {
        this.offsetY = Either.left(offsetY);
        return this;
    }

    public MultiblockBuilder offsetY(String offsetY) {
        this.offsetY = Either.right(offsetY);
        return this;
    }

    public MultiblockBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public MultiblockBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    public MultiblockBuilder height(int height) {
        this.height = Either.left(height);
        return this;
    }

    public MultiblockBuilder height(String height) {
        this.height = Either.right(height);
        return this;
    }

    public MultiblockBuilder viewAngle(float viewAngle) {
        this.viewAngle = Either.left(viewAngle);
        return this;
    }

    public MultiblockBuilder viewAngle(String viewAngle) {
        this.viewAngle = Either.right(viewAngle);
        return this;
    }

    public MultiblockBuilder noOffsets(boolean noOffsets) {
        this.noOffsets = Either.left(noOffsets);
        return this;
    }

    public MultiblockBuilder noOffsets(String noOffsets) {
        this.noOffsets = Either.right(noOffsets);
        return this;
    }

    @Override
    protected void build(JsonObject json) {
        if(multiblock != null)
            json.add("multiblock", resolve(multiblock).orElseGet(() -> Multiblock.codec().encodeStart(JsonOps.INSTANCE, multiblock.left().orElseThrow()).getOrThrow()));
        if(multiblockId != null)
            json.add("multiblock_id", resolve(multiblockId).orElseGet(() -> ResourceLocation.CODEC.encodeStart(JsonOps.INSTANCE, multiblockId.left().orElseThrow()).getOrThrow()));
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
