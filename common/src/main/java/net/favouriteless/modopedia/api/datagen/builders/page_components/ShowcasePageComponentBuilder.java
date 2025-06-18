package net.favouriteless.modopedia.api.datagen.builders.page_components;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.ItemPageComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ShowcasePageComponentBuilder extends PageComponentBuilder {

    private final Either<List<ItemStack>, String> items;
    private Either<Integer, String> width;
    private Either<Integer, String> height;
    private Either<Float, String> scale;

    private ShowcasePageComponentBuilder(ItemStack... items) {
        super(ItemPageComponent.ID);
        this.items = Either.left(List.of(items));
    }

    private ShowcasePageComponentBuilder(String items) {
        super(ItemPageComponent.ID);
        this.items = Either.right(items);
    }

    public static ShowcasePageComponentBuilder of(ItemStack... items) {
        return new ShowcasePageComponentBuilder(items);
    }

    public static ShowcasePageComponentBuilder of(String items) {
        return new ShowcasePageComponentBuilder(items);
    }

    @Override
    public ShowcasePageComponentBuilder x(int x) {
        return (ShowcasePageComponentBuilder)super.x(x);
    }

    @Override
    public ShowcasePageComponentBuilder x(String x) {
        return (ShowcasePageComponentBuilder)super.x(x);
    }

    @Override
    public ShowcasePageComponentBuilder y(int y) {
        return (ShowcasePageComponentBuilder)super.y(y);
    }

    @Override
    public ShowcasePageComponentBuilder y(String y) {
        return (ShowcasePageComponentBuilder)super.y(y);
    }

    public ShowcasePageComponentBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public ShowcasePageComponentBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    public ShowcasePageComponentBuilder height(int height) {
        this.height = Either.left(height);
        return this;
    }

    public ShowcasePageComponentBuilder height(String height) {
        this.height = Either.right(height);
        return this;
    }

    public ShowcasePageComponentBuilder centered(float scale) {
        this.scale = Either.left(scale);
        return this;
    }

    public ShowcasePageComponentBuilder scale(String scale) {
        this.scale = Either.right(scale);
        return this;
    }

    @Override
    protected void build(JsonObject json) {
        json.add("items", resolve(items).orElse(ItemStack.CODEC.listOf().encodeStart(JsonOps.INSTANCE, items.left().orElseThrow()).getOrThrow()));
        if(width != null)
            resolveNum(width).ifPresent(w -> json.add("width", w));
        if(height != null)
            resolveNum(height).ifPresent(h -> json.add("height", h));
        if(scale != null)
            resolveNum(scale).ifPresent(s -> json.add("scale", s));
    }

}
