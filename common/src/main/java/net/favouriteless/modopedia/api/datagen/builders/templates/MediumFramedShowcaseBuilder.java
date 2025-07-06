package net.favouriteless.modopedia.api.datagen.builders.templates;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.common.datagen.builders.TemplateComponentBuilder;

import net.minecraft.resources.*;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class MediumFramedShowcaseBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation ID = Modopedia.id("medium_framed_showcase");

    private final Either<List<ItemStack>, String> items;
    private Either<Integer, String> width;
    private Either<Float, String> scale;

    private MediumFramedShowcaseBuilder(ItemStack... items) {
        super(ID);
        this.items = Either.left(List.of(items));
    }

    private MediumFramedShowcaseBuilder(String items) {
        super(ID);
        this.items = Either.right(items);
    }

    public static MediumFramedShowcaseBuilder of(ItemStack... items) {
        return new MediumFramedShowcaseBuilder(items);
    }

    public static MediumFramedShowcaseBuilder of(String type) {
        return new MediumFramedShowcaseBuilder(type);
    }

    @Override
    public MediumFramedShowcaseBuilder x(int x) {
        return (MediumFramedShowcaseBuilder)super.x(x);
    }

    @Override
    public MediumFramedShowcaseBuilder x(String x) {
        return (MediumFramedShowcaseBuilder)super.x(x);
    }

    @Override
    public MediumFramedShowcaseBuilder y(int y) {
        return (MediumFramedShowcaseBuilder)super.y(y);
    }

    @Override
    public MediumFramedShowcaseBuilder y(String y) {
        return (MediumFramedShowcaseBuilder)super.y(y);
    }

    public MediumFramedShowcaseBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public MediumFramedShowcaseBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    public MediumFramedShowcaseBuilder scale(float scale) {
        this.scale = Either.left(scale);
        return this;
    }

    public MediumFramedShowcaseBuilder scale(String scale) {
        this.scale = Either.right(scale);
        return this;
    }

    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {
        json.add("items", resolve(items).orElseGet(() -> ItemStack.CODEC.listOf().encodeStart(ops, orThrow(items)).getOrThrow()));
        if(width != null)
            resolveNum(width).ifPresent(w -> json.add("width", w));
        if(scale != null)
            resolveNum(scale).ifPresent(s -> json.add("scale", s));
    }

}
