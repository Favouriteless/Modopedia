package net.favouriteless.modopedia.api.datagen.builders.page_components.components;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.ItemPageComponent;

import net.minecraft.resources.RegistryOps;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder extends PageComponentBuilder {

    private Either<List<List<ItemStack>>, String> items = Either.left(new ArrayList<>());
    private Either<Integer, String> rowMax;
    private Either<Integer, String> padding;
    private Either<Boolean, String> centered;
    private Either<Boolean, String> reverseY;

    private ItemBuilder() {
        super(ItemPageComponent.ID);
    }

    private ItemBuilder(String items) {
        super(ItemPageComponent.ID);
        this.items = Either.right(items);
    }

    public static ItemBuilder of(ItemStack... items) {
        return new ItemBuilder().items(items);
    }

    public static ItemBuilder of(String items) {
        return new ItemBuilder(items);
    }

    public ItemBuilder items(ItemStack... items) {
        if(this.items.left().isEmpty())
            this.items = Either.left(new ArrayList<>());
        this.items.left().ifPresent(l -> l.add(List.of(items)));
        return this;
    }

    @Override
    public ItemBuilder x(int x) {
        return (ItemBuilder)super.x(x);
    }

    @Override
    public ItemBuilder x(String x) {
        return (ItemBuilder)super.x(x);
    }

    @Override
    public ItemBuilder y(int y) {
        return (ItemBuilder)super.y(y);
    }

    @Override
    public ItemBuilder y(String y) {
        return (ItemBuilder)super.y(y);
    }

    public ItemBuilder rowMax(int rowMax) {
        this.rowMax = Either.left(rowMax);
        return this;
    }

    public ItemBuilder rowMax(String rowMax) {
        this.rowMax = Either.right(rowMax);
        return this;
    }

    public ItemBuilder padding(int padding) {
        this.padding = Either.left(padding);
        return this;
    }

    public ItemBuilder padding(String padding) {
        this.padding = Either.right(padding);
        return this;
    }

    public ItemBuilder centered(boolean centered) {
        this.centered = Either.left(centered);
        return this;
    }

    public ItemBuilder centered(String centered) {
        this.centered = Either.right(centered);
        return this;
    }

    public ItemBuilder reverseY(boolean reverseY) {
        this.reverseY = Either.left(reverseY);
        return this;
    }

    public ItemBuilder reverseY(String reverseY) {
        this.reverseY = Either.right(reverseY);
        return this;
    }

    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {
        json.add("items", resolve(items, l -> ItemStack.CODEC.listOf().listOf().encodeStart(ops, l).getOrThrow()));

        if(rowMax != null)
            json.add("row_max", resolveNum(rowMax));
        if(padding != null)
            json.add("padding", resolveNum(padding));
        if(centered != null)
            json.add("centered", resolveBool(centered));
        if(reverseY != null)
            json.add("reverseY", resolveBool(reverseY));
    }

}
