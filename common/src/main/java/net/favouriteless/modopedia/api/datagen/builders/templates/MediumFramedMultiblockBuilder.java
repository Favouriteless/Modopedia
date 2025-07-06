package net.favouriteless.modopedia.api.datagen.builders.templates;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.common.datagen.builders.TemplateComponentBuilder;
import net.favouriteless.modopedia.api.multiblock.Multiblock;

import net.minecraft.resources.*;

public class MediumFramedMultiblockBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation ID = Modopedia.id("medium_framed_multiblock");

    private Either<Integer, String> width;

    private Either<Multiblock, String> multiblock;
    private Either<ResourceLocation, String> multiblockId;
    private Either<Float, String> offsetX;
    private Either<Float, String> offsetY;
    private Either<Float, String> scale;
    private Either<Float, String> viewAngle;
    private Either<Boolean, String> noOffsets;

    protected MediumFramedMultiblockBuilder() {
        super(ID);
    }

    public static MediumFramedMultiblockBuilder of() {
        return new MediumFramedMultiblockBuilder();
    }

    @Override
    public MediumFramedMultiblockBuilder x(int x) {
        return (MediumFramedMultiblockBuilder)super.x(x);
    }

    @Override
    public MediumFramedMultiblockBuilder x(String x) {
        return (MediumFramedMultiblockBuilder)super.x(x);
    }

    @Override
    public MediumFramedMultiblockBuilder y(int y) {
        return (MediumFramedMultiblockBuilder)super.y(y);
    }

    @Override
    public MediumFramedMultiblockBuilder y(String y) {
        return (MediumFramedMultiblockBuilder)super.y(y);
    }

    public MediumFramedMultiblockBuilder multiblock(Multiblock multiblock) {
        this.multiblock = Either.left(multiblock);
        return this;
    }

    public MediumFramedMultiblockBuilder multiblock(String multiblock) {
        this.multiblock = Either.right(multiblock);
        return this;
    }

    public MediumFramedMultiblockBuilder multiblockId(ResourceLocation multiblock) {
        this.multiblockId = Either.left(multiblock);
        return this;
    }

    public MediumFramedMultiblockBuilder multiblockId(String multiblock) {
        this.multiblockId = Either.right(multiblock);
        return this;
    }

    public MediumFramedMultiblockBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public MediumFramedMultiblockBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    public MediumFramedMultiblockBuilder scale(float scale) {
        this.scale = Either.left(scale);
        return this;
    }

    public MediumFramedMultiblockBuilder scale(String scale) {
        this.scale = Either.right(scale);
        return this;
    }

    public MediumFramedMultiblockBuilder offsetX(float offsetX) {
        this.offsetX = Either.left(offsetX);
        return this;
    }

    public MediumFramedMultiblockBuilder offsetX(String offsetX) {
        this.offsetX = Either.right(offsetX);
        return this;
    }

    public MediumFramedMultiblockBuilder offsetY(float offsetY) {
        this.offsetY = Either.left(offsetY);
        return this;
    }

    public MediumFramedMultiblockBuilder offsetY(String offsetY) {
        this.offsetY = Either.right(offsetY);
        return this;
    }

    public MediumFramedMultiblockBuilder viewAngle(float viewAngle) {
        this.viewAngle = Either.left(viewAngle);
        return this;
    }

    public MediumFramedMultiblockBuilder viewAngle(String viewAngle) {
        this.viewAngle = Either.right(viewAngle);
        return this;
    }

    public MediumFramedMultiblockBuilder noOffsets(boolean noOffsets) {
        this.noOffsets = Either.left(noOffsets);
        return this;
    }

    public MediumFramedMultiblockBuilder noOffsets(String noOffsets) {
        this.noOffsets = Either.right(noOffsets);
        return this;
    }

    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {
        if(width != null)
            resolveNum(width).ifPresent(w -> json.add("width", w));
        if(multiblock != null)
            json.add("multiblock", resolve(multiblock).orElseGet(() -> Multiblock.codec().encodeStart(ops, orThrow(multiblock)).getOrThrow()));
        if(multiblockId != null)
            json.add("multiblock_id", resolve(multiblockId).orElseGet(() -> ResourceLocation.CODEC.encodeStart(ops, orThrow(multiblockId)).getOrThrow()));
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
