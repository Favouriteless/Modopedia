package net.favouriteless.modopedia.api.datagen.builders.templates.page;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.client.multiblock.BlockStateCodec;
import net.favouriteless.modopedia.common.datagen.builders.TemplateComponentBuilder;

import net.minecraft.resources.*;
import net.minecraft.world.level.block.state.BlockState;

public class BlockPageBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation ID = Modopedia.id("page/block");

    private final String text;
    private final Either<BlockState, String> block;

    private Either<Integer, String> height;
    private Either<Integer, String> textOffset;

    private Either<Float, String> offsetX;
    private Either<Float, String> offsetY;
    private Either<Float, String> scale;
    private Either<Float, String> viewAngle;

    protected BlockPageBuilder(String text, BlockState block) {
        super(ID);
        this.text = text;
        this.block = Either.left(block);
    }

    protected BlockPageBuilder(String text, String block) {
        super(ID);
        this.text = text;
        this.block = Either.right(block);
    }

    public static BlockPageBuilder of(String text, BlockState block) {
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
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {
        json.add("text", resolveString(text));

        if(block != null)
            json.add("block", resolve(block, b -> BlockStateCodec.CODEC.encodeStart(ops, b).getOrThrow()));
        if(textOffset != null)
            json.add("text_offset", resolveNum(textOffset));
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
    }

}
