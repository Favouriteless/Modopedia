package net.favouriteless.modopedia.api.datagen.builders.templates.page;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.datagen.builders.TemplateComponentBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class BlockPageBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation ID = Modopedia.id("page/block");

    private final String text;
    private final Either<Block, String> block;

    private Either<Integer, String> width;
    private Either<Integer, String> height;
    private Either<Integer, String> textOffset;

    private Either<Float, String> offsetX;
    private Either<Float, String> offsetY;
    private Either<Float, String> scale;
    private Either<Float, String> viewAngle;

    protected BlockPageBuilder(String text, Block block) {
        super(ID);
        this.text = text;
        this.block = Either.left(block);
    }

    protected BlockPageBuilder(String text, String block) {
        super(ID);
        this.text = text;
        this.block = Either.right(block);
    }

    public static BlockPageBuilder of(String text, Block block) {
        return new BlockPageBuilder(text, block);
    }

    public static BlockPageBuilder of(String text, String block) {
        return new BlockPageBuilder(text, block);
    }

    @Override
    public BlockPageBuilder x(int x) {
        return (BlockPageBuilder)super.x(x);
    }

    @Override
    public BlockPageBuilder x(String x) {
        return (BlockPageBuilder)super.x(x);
    }

    @Override
    public BlockPageBuilder y(int y) {
        return (BlockPageBuilder)super.y(y);
    }

    @Override
    public BlockPageBuilder y(String y) {
        return (BlockPageBuilder)super.y(y);
    }

    public BlockPageBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public BlockPageBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    public BlockPageBuilder height(int height) {
        this.height = Either.left(height);
        return this;
    }

    public BlockPageBuilder height(String height) {
        this.height = Either.right(height);
        return this;
    }

    public BlockPageBuilder scale(float scale) {
        this.scale = Either.left(scale);
        return this;
    }

    public BlockPageBuilder scale(String scale) {
        this.scale = Either.right(scale);
        return this;
    }

    public BlockPageBuilder offsetX(float offsetX) {
        this.offsetX = Either.left(offsetX);
        return this;
    }

    public BlockPageBuilder offsetX(String offsetX) {
        this.offsetX = Either.right(offsetX);
        return this;
    }

    public BlockPageBuilder offsetY(float offsetY) {
        this.offsetY = Either.left(offsetY);
        return this;
    }

    public BlockPageBuilder offsetY(String offsetY) {
        this.offsetY = Either.right(offsetY);
        return this;
    }

    public BlockPageBuilder viewAngle(float viewAngle) {
        this.viewAngle = Either.left(viewAngle);
        return this;
    }

    public BlockPageBuilder viewAngle(String viewAngle) {
        this.viewAngle = Either.right(viewAngle);
        return this;
    }

    public BlockPageBuilder textOffset(int textOffset) {
        this.textOffset = Either.left(textOffset);
        return this;
    }

    public BlockPageBuilder textOffset(String textOffset) {
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
        if(block != null)
            json.add("block", resolve(block).orElseGet(() -> ResourceLocation.CODEC.encodeStart(JsonOps.INSTANCE, BuiltInRegistries.BLOCK.getKey(orThrow(block))).getOrThrow()));
        if(offsetX != null)
            resolveNum(offsetX).ifPresent(o -> json.add("offset_x", o));
        if(offsetY != null)
            resolveNum(offsetY).ifPresent(o -> json.add("offset_y", o));
        if(scale != null)
            resolveNum(scale).ifPresent(s -> json.add("scale", s));
        if(viewAngle != null)
            resolveNum(viewAngle).ifPresent(v -> json.add("view_angle", v));
    }

}
