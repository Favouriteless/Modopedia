package net.favouriteless.modopedia.api.datagen.builders.templates;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.datagen.builders.page_components.TemplateComponentBuilder;
import net.favouriteless.modopedia.api.multiblock.Multiblock;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;

public class FramedMultiblockBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation LARGE_ID = Modopedia.id("large_framed_multiblock");
    public static final ResourceLocation MEDIUM_ID = Modopedia.id("medium_framed_multiblock");

    private Either<Integer, String> width;

    private Either<Multiblock, String> multiblock;
    private Either<ResourceLocation, String> multiblockId;
    private Either<Float, String> offsetX;
    private Either<Float, String> offsetY;
    private Either<Float, String> scale;
    private Either<Float, String> viewAngle;
    private Either<Boolean, String> noOffsets;

    protected FramedMultiblockBuilder(ResourceLocation id) {
        super(id);
    }

    public static FramedMultiblockBuilder large() {
        return new FramedMultiblockBuilder(LARGE_ID);
    }

    public static FramedMultiblockBuilder medium() {
        return new FramedMultiblockBuilder(MEDIUM_ID);
    }

    @Override
    public FramedMultiblockBuilder x(int x) {
        return (FramedMultiblockBuilder)super.x(x);
    }

    @Override
    public FramedMultiblockBuilder x(String x) {
        return (FramedMultiblockBuilder)super.x(x);
    }

    @Override
    public FramedMultiblockBuilder y(int y) {
        return (FramedMultiblockBuilder)super.y(y);
    }

    @Override
    public FramedMultiblockBuilder y(String y) {
        return (FramedMultiblockBuilder)super.y(y);
    }

    public FramedMultiblockBuilder multiblock(Multiblock multiblock) {
        this.multiblock = Either.left(multiblock);
        return this;
    }

    public FramedMultiblockBuilder multiblock(String multiblock) {
        this.multiblock = Either.right(multiblock);
        return this;
    }

    public FramedMultiblockBuilder multiblockId(ResourceLocation multiblock) {
        this.multiblockId = Either.left(multiblock);
        return this;
    }

    public FramedMultiblockBuilder multiblockId(String multiblock) {
        this.multiblockId = Either.right(multiblock);
        return this;
    }

    public FramedMultiblockBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public FramedMultiblockBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    public FramedMultiblockBuilder scale(float scale) {
        this.scale = Either.left(scale);
        return this;
    }

    public FramedMultiblockBuilder scale(String scale) {
        this.scale = Either.right(scale);
        return this;
    }

    public FramedMultiblockBuilder offsetX(float offsetX) {
        this.offsetX = Either.left(offsetX);
        return this;
    }

    public FramedMultiblockBuilder offsetX(String offsetX) {
        this.offsetX = Either.right(offsetX);
        return this;
    }

    public FramedMultiblockBuilder offsetY(float offsetY) {
        this.offsetY = Either.left(offsetY);
        return this;
    }

    public FramedMultiblockBuilder offsetY(String offsetY) {
        this.offsetY = Either.right(offsetY);
        return this;
    }

    public FramedMultiblockBuilder viewAngle(float viewAngle) {
        this.viewAngle = Either.left(viewAngle);
        return this;
    }

    public FramedMultiblockBuilder viewAngle(String viewAngle) {
        this.viewAngle = Either.right(viewAngle);
        return this;
    }

    public FramedMultiblockBuilder noOffsets(boolean noOffsets) {
        this.noOffsets = Either.left(noOffsets);
        return this;
    }

    public FramedMultiblockBuilder noOffsets(String noOffsets) {
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
