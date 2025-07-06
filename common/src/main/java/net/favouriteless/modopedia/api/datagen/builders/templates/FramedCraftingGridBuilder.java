package net.favouriteless.modopedia.api.datagen.builders.templates;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.common.datagen.builders.TemplateComponentBuilder;

import net.minecraft.resources.*;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class FramedCraftingGridBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation ID = Modopedia.id("framed_crafting_grid");

    private final Either<List<List<ItemStack>>, String> items;

    private FramedCraftingGridBuilder(List<List<ItemStack>> items) {
        super(ID);
        this.items = Either.left(items);
    }

    private FramedCraftingGridBuilder(String items) {
        super(ID);
        this.items = Either.right(items);
    }

    public static FramedCraftingGridBuilder of(List<List<ItemStack>> items) {
        return new FramedCraftingGridBuilder(items);
    }

    public static FramedCraftingGridBuilder of(String items) {
        return new FramedCraftingGridBuilder(items);
    }

    @Override
    public FramedCraftingGridBuilder x(int x) {
        return (FramedCraftingGridBuilder)super.x(x);
    }

    @Override
    public FramedCraftingGridBuilder x(String x) {
        return (FramedCraftingGridBuilder)super.x(x);
    }

    @Override
    public FramedCraftingGridBuilder y(int y) {
        return (FramedCraftingGridBuilder)super.y(y);
    }

    @Override
    public FramedCraftingGridBuilder y(String y) {
        return (FramedCraftingGridBuilder)super.y(y);
    }

    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {
        json.add("items", resolve(items, l -> ItemStack.CODEC.listOf().listOf().encodeStart(ops, l).getOrThrow()));
    }

}
