package net.favouriteless.modopedia.api.datagen.builders.templates;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.datagen.builders.TemplateComponentBuilder;
import net.minecraft.resources.ResourceLocation;
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
    protected void build(JsonObject json) {
        json.add("items", resolve(items).orElseGet(() -> ItemStack.CODEC.listOf().listOf().encodeStart(JsonOps.INSTANCE, orThrow(items)).getOrThrow()));

        if(width != null)
            resolveNum(width).ifPresent(w -> json.add("width", w));
    }

}
