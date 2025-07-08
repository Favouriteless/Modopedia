package net.favouriteless.modopedia.api.datagen.builders.templates;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.datagen.builders.page_components.TemplateComponentBuilder;

import net.minecraft.resources.*;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class LargeFramedShowcaseBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation ID = Modopedia.id("large_framed_showcase");

    private final Either<List<ItemStack>, String> items;
    private Either<Integer, String> width;
    private Either<Float, String> scale;

    private LargeFramedShowcaseBuilder(ItemStack... items) {
        super(ID);
        this.items = Either.left(List.of(items));
    }

    private LargeFramedShowcaseBuilder(String items) {
        super(ID);
        this.items = Either.right(items);
    }

    public static LargeFramedShowcaseBuilder of(ItemStack... items) {
        return new LargeFramedShowcaseBuilder(items);
    }

    public static LargeFramedShowcaseBuilder of(String type) {
        return new LargeFramedShowcaseBuilder(type);
    }

    @Override
    public LargeFramedShowcaseBuilder x(int x) {
        return (LargeFramedShowcaseBuilder)super.x(x);
    }

    @Override
    public LargeFramedShowcaseBuilder x(String x) {
        return (LargeFramedShowcaseBuilder)super.x(x);
    }

    @Override
    public LargeFramedShowcaseBuilder y(int y) {
        return (LargeFramedShowcaseBuilder)super.y(y);
    }

    @Override
    public LargeFramedShowcaseBuilder y(String y) {
        return (LargeFramedShowcaseBuilder)super.y(y);
    }

    public LargeFramedShowcaseBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public LargeFramedShowcaseBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    public LargeFramedShowcaseBuilder scale(float scale) {
        this.scale = Either.left(scale);
        return this;
    }

    public LargeFramedShowcaseBuilder scale(String scale) {
        this.scale = Either.right(scale);
        return this;
    }

    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {
        json.add("items", resolve(items, l -> ItemStack.CODEC.listOf().encodeStart(ops, l).getOrThrow()));

        if(width != null)
            json.add("width", resolveNum(width));
        if(scale != null)
            json.add("scale", resolveNum(scale));
    }

}
