package net.favouriteless.modopedia.api.datagen.builders.page_components.components;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.api.multiblock.Multiblock;
import net.favouriteless.modopedia.client.page_components.MultiblockPageComponent;

import net.minecraft.resources.*;

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

    public static MultiblockBuilder of() {
        return new MultiblockBuilder();
    }

    public MultiblockBuilder multiblock(Multiblock multiblock) {
        this.multiblock = Either.left(multiblock);
        return this;
    }

    public MultiblockBuilder multiblock(String multiblock) {
        this.multiblock = Either.right(multiblock);
        return this;
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
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {
        if(multiblock == null && multiblockId == null)
            throw new IllegalStateException("MultiblockBuilder needs either a multiblock or multiblockId");

        if(multiblock != null)
            json.add("multiblock", resolve(multiblock, m -> Multiblock.codec().encodeStart(ops, m).getOrThrow()));
        if(multiblockId != null)
            json.add("multiblock_id", resolveResourceLocation(multiblockId));
        if(width != null)
            json.add("width", resolveNum(width));
        if(height != null)
            json.add("height", resolveNum(height));
        if(offsetX != null)
            json.add("offset_x", resolveNum(offsetX));
        if(offsetY != null)
            json.add("offset_y", resolveNum(offsetY));
        if(scale != null)
            json.add("scale", resolveNum(scale));
        if(viewAngle != null)
            json.add("view_angle", resolveNum(viewAngle));
        if(noOffsets != null)
            json.add("no_offsets", resolveBool(noOffsets));
    }

}
