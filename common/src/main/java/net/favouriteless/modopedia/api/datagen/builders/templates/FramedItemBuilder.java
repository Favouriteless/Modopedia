package net.favouriteless.modopedia.api.datagen.builders.templates;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.common.datagen.builders.TemplateComponentBuilder;

import net.minecraft.resources.*;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class FramedItemBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation ID = Modopedia.id("framed_item");

    private final Either<List<List<ItemStack>>, String> items;
    private Either<Integer, String> width;

    protected FramedItemBuilder(ItemStack... items) {
        super(ID);
        this.items = Either.left(List.of(List.of(items)));
    }

    protected FramedItemBuilder(String items) {
        super(ID);
        this.items = Either.right(items);
    }

    public static FramedItemBuilder of(ItemStack... items) {
        return new FramedItemBuilder(items);
    }

    public static FramedItemBuilder of(String items) {
        return new FramedItemBuilder(items);
    }

    public FramedItemBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public FramedItemBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {
        json.add("items", resolve(items, l -> ItemStack.CODEC.listOf().listOf().encodeStart(ops, l).getOrThrow()));

        if(width != null)
            json.add("width", resolveNum(width));
    }

}
