package net.favouriteless.modopedia.api.datagen.builders.templates;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.common.datagen.builders.TemplateComponentBuilder;
import net.favouriteless.modopedia.api.multiblock.Multiblock;

import net.minecraft.resources.*;

public class LargeFramedMultiblockBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation ID = Modopedia.id("large_framed_multiblock");

    private Either<Integer, String> width;

    private Either<Multiblock, String> multiblock;
    private Either<ResourceLocation, String> multiblockId;
    private Either<Float, String> offsetX;
    private Either<Float, String> offsetY;
    private Either<Float, String> scale;
    private Either<Float, String> viewAngle;
    private Either<Boolean, String> noOffsets;

    protected LargeFramedMultiblockBuilder() {
        super(ID);
    }

    public static LargeFramedMultiblockBuilder of() {
        return new LargeFramedMultiblockBuilder();
    }

    @Override
    public LargeFramedMultiblockBuilder x(int x) {
        return (LargeFramedMultiblockBuilder)super.x(x);
    }

    @Override
    public LargeFramedMultiblockBuilder x(String x) {
        return (LargeFramedMultiblockBuilder)super.x(x);
    }

    @Override
    public LargeFramedMultiblockBuilder y(int y) {
        return (LargeFramedMultiblockBuilder)super.y(y);
    }

    @Override
    public LargeFramedMultiblockBuilder y(String y) {
        return (LargeFramedMultiblockBuilder)super.y(y);
    }

    public LargeFramedMultiblockBuilder multiblock(Multiblock multiblock) {
        this.multiblock = Either.left(multiblock);
        return this;
    }

    public LargeFramedMultiblockBuilder multiblock(String multiblock) {
        this.multiblock = Either.right(multiblock);
        return this;
    }

    public LargeFramedMultiblockBuilder multiblockId(ResourceLocation multiblock) {
        this.multiblockId = Either.left(multiblock);
        return this;
    }

    public LargeFramedMultiblockBuilder multiblockId(String multiblock) {
        this.multiblockId = Either.right(multiblock);
        return this;
    }

    public LargeFramedMultiblockBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public LargeFramedMultiblockBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    public LargeFramedMultiblockBuilder scale(float scale) {
        this.scale = Either.left(scale);
        return this;
    }

    public LargeFramedMultiblockBuilder scale(String scale) {
        this.scale = Either.right(scale);
        return this;
    }

    public LargeFramedMultiblockBuilder offsetX(float offsetX) {
        this.offsetX = Either.left(offsetX);
        return this;
    }

    public LargeFramedMultiblockBuilder offsetX(String offsetX) {
        this.offsetX = Either.right(offsetX);
        return this;
    }

    public LargeFramedMultiblockBuilder offsetY(float offsetY) {
        this.offsetY = Either.left(offsetY);
        return this;
    }

    public LargeFramedMultiblockBuilder offsetY(String offsetY) {
        this.offsetY = Either.right(offsetY);
        return this;
    }

    public LargeFramedMultiblockBuilder viewAngle(float viewAngle) {
        this.viewAngle = Either.left(viewAngle);
        return this;
    }

    public LargeFramedMultiblockBuilder viewAngle(String viewAngle) {
        this.viewAngle = Either.right(viewAngle);
        return this;
    }

    public LargeFramedMultiblockBuilder noOffsets(boolean noOffsets) {
        this.noOffsets = Either.left(noOffsets);
        return this;
    }

    public LargeFramedMultiblockBuilder noOffsets(String noOffsets) {
        this.noOffsets = Either.right(noOffsets);
        return this;
    }

    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {
        if(width != null)
            json.add("width", resolveNum(width));
        if(multiblock != null)
            json.add("multiblock", resolve(multiblock, m -> Multiblock.codec().encodeStart(ops, m).getOrThrow()));
        if(multiblockId != null)
            json.add("multiblock_id", resolveResourceLocation(multiblockId));
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
