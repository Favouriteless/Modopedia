package net.favouriteless.modopedia.api.datagen.builders.templates.page;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.multiblock.Multiblock;
import net.favouriteless.modopedia.datagen.builders.TemplateComponentBuilder;
import net.minecraft.resources.ResourceLocation;

public class MultiblockPageBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation ID = Modopedia.id("page/multiblock");

    private final String text;
    private Either<Integer, String> width;
    private Either<Integer, String> height;
    private Either<Integer, String> textOffset;

    private Either<Multiblock, String> multiblock;
    private Either<ResourceLocation, String> multiblockId;
    private Either<Float, String> offsetX;
    private Either<Float, String> offsetY;
    private Either<Float, String> scale;
    private Either<Float, String> viewAngle;
    private Either<Boolean, String> noOffsets;

    protected MultiblockPageBuilder(String text) {
        super(ID);
        this.text = text;
    }

    public static MultiblockPageBuilder of(String text) {
        return new MultiblockPageBuilder(text);
    }

    @Override
    public MultiblockPageBuilder x(int x) {
        return (MultiblockPageBuilder)super.x(x);
    }

    @Override
    public MultiblockPageBuilder x(String x) {
        return (MultiblockPageBuilder)super.x(x);
    }

    @Override
    public MultiblockPageBuilder y(int y) {
        return (MultiblockPageBuilder)super.y(y);
    }

    @Override
    public MultiblockPageBuilder y(String y) {
        return (MultiblockPageBuilder)super.y(y);
    }

    public MultiblockPageBuilder multiblock(Multiblock multiblock) {
        this.multiblock = Either.left(multiblock);
        return this;
    }

    public MultiblockPageBuilder multiblock(String multiblock) {
        this.multiblock = Either.right(multiblock);
        return this;
    }

    public MultiblockPageBuilder multiblockId(ResourceLocation multiblock) {
        this.multiblockId = Either.left(multiblock);
        return this;
    }

    public MultiblockPageBuilder multiblockId(String multiblock) {
        this.multiblockId = Either.right(multiblock);
        return this;
    }

    public MultiblockPageBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public MultiblockPageBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    public MultiblockPageBuilder height(int height) {
        this.height = Either.left(height);
        return this;
    }

    public MultiblockPageBuilder height(String height) {
        this.height = Either.right(height);
        return this;
    }

    public MultiblockPageBuilder scale(float scale) {
        this.scale = Either.left(scale);
        return this;
    }

    public MultiblockPageBuilder scale(String scale) {
        this.scale = Either.right(scale);
        return this;
    }

    public MultiblockPageBuilder offsetX(float offsetX) {
        this.offsetX = Either.left(offsetX);
        return this;
    }

    public MultiblockPageBuilder offsetX(String offsetX) {
        this.offsetX = Either.right(offsetX);
        return this;
    }

    public MultiblockPageBuilder offsetY(float offsetY) {
        this.offsetY = Either.left(offsetY);
        return this;
    }

    public MultiblockPageBuilder offsetY(String offsetY) {
        this.offsetY = Either.right(offsetY);
        return this;
    }

    public MultiblockPageBuilder viewAngle(float viewAngle) {
        this.viewAngle = Either.left(viewAngle);
        return this;
    }

    public MultiblockPageBuilder viewAngle(String viewAngle) {
        this.viewAngle = Either.right(viewAngle);
        return this;
    }

    public MultiblockPageBuilder noOffsets(boolean noOffsets) {
        this.noOffsets = Either.left(noOffsets);
        return this;
    }

    public MultiblockPageBuilder noOffsets(String noOffsets) {
        this.noOffsets = Either.right(noOffsets);
        return this;
    }

    public MultiblockPageBuilder textOffset(int textOffset) {
        this.textOffset = Either.left(textOffset);
        return this;
    }

    public MultiblockPageBuilder textOffset(String textOffset) {
        this.textOffset = Either.right(textOffset);
        return this;
    }

    @Override
    protected void build(JsonObject json) {
        json.add("text", new JsonPrimitive(text));
        if(textOffset != null)
            resolveNum(textOffset).ifPresent(o -> json.add("text_offset", o));
        if(width != null)
            resolveNum(width).ifPresent(w -> json.add("width", w));
        if(height != null)
            resolveNum(height).ifPresent(h -> json.add("height", h));
        if(multiblock != null)
            json.add("multiblock", resolve(multiblock).orElseGet(() -> Multiblock.codec().encodeStart(JsonOps.INSTANCE, orThrow(multiblock)).getOrThrow()));
        if(multiblockId != null)
            json.add("multiblock_id", resolve(multiblockId).orElseGet(() -> ResourceLocation.CODEC.encodeStart(JsonOps.INSTANCE, orThrow(multiblockId)).getOrThrow()));
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
