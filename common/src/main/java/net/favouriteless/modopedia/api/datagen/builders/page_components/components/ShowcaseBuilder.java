package net.favouriteless.modopedia.api.datagen.builders.page_components.components;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.ShowcasePageComponent;

import net.minecraft.resources.RegistryOps;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ShowcaseBuilder extends PageComponentBuilder {

    private final Either<List<ItemStack>, String> items;
    private Either<Integer, String> width;
    private Either<Integer, String> height;
    private Either<Float, String> scale;

    private ShowcaseBuilder(ItemStack... items) {
        super(ShowcasePageComponent.ID);
        this.items = Either.left(List.of(items));
    }

    private ShowcaseBuilder(String items) {
        super(ShowcasePageComponent.ID);
        this.items = Either.right(items);
    }

    public static ShowcaseBuilder of(ItemStack... items) {
        return new ShowcaseBuilder(items);
    }

    public static ShowcaseBuilder of(String items) {
        return new ShowcaseBuilder(items);
    }

    @Override
    public ShowcaseBuilder x(int x) {
        return (ShowcaseBuilder)super.x(x);
    }

    @Override
    public ShowcaseBuilder x(String x) {
        return (ShowcaseBuilder)super.x(x);
    }

    @Override
    public ShowcaseBuilder y(int y) {
        return (ShowcaseBuilder)super.y(y);
    }

    @Override
    public ShowcaseBuilder y(String y) {
        return (ShowcaseBuilder)super.y(y);
    }

    public ShowcaseBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public ShowcaseBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    public ShowcaseBuilder height(int height) {
        this.height = Either.left(height);
        return this;
    }

    public ShowcaseBuilder height(String height) {
        this.height = Either.right(height);
        return this;
    }

    public ShowcaseBuilder scale(float scale) {
        this.scale = Either.left(scale);
        return this;
    }

    public ShowcaseBuilder scale(String scale) {
        this.scale = Either.right(scale);
        return this;
    }

    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {
        json.add("items", resolve(items, l -> ItemStack.CODEC.listOf().encodeStart(ops, l).getOrThrow()));

        if(width != null)
            json.add("width", resolveNum(width));
        if(height != null)
            json.add("height", resolveNum(height));
        if(scale != null)
            json.add("scale", resolveNum(scale));
    }

}
