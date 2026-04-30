package net.favouriteless.modopedia.api.datagen.builders.templates;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.datagen.builders.page_components.TemplateComponentBuilder;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class FramedShowcaseBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation LARGE_ID = Modopedia.id("large_framed_showcase");
    public static final ResourceLocation MEDIUM_ID = Modopedia.id("medium_framed_showcase");

    private final Either<List<ItemStack>, String> items;
    private Either<Integer, String> width;
    private Either<Float, String> scale;

    private FramedShowcaseBuilder(ResourceLocation id, ItemStack... items) {
        super(id);
        this.items = Either.left(List.of(items));
    }

    private FramedShowcaseBuilder(ResourceLocation id, String items) {
        super(id);
        this.items = Either.right(items);
    }

    public static FramedShowcaseBuilder large(ItemStack... items) {
        return new FramedShowcaseBuilder(LARGE_ID, items);
    }

    public static FramedShowcaseBuilder large(String type) {
        return new FramedShowcaseBuilder(LARGE_ID, type);
    }

    public static FramedShowcaseBuilder medium(ItemStack... items) {
        return new FramedShowcaseBuilder(MEDIUM_ID, items);
    }

    public static FramedShowcaseBuilder medium(String type) {
        return new FramedShowcaseBuilder(MEDIUM_ID, type);
    }

    @Override
    public FramedShowcaseBuilder x(int x) {
        return (FramedShowcaseBuilder)super.x(x);
    }

    @Override
    public FramedShowcaseBuilder x(String x) {
        return (FramedShowcaseBuilder)super.x(x);
    }

    @Override
    public FramedShowcaseBuilder y(int y) {
        return (FramedShowcaseBuilder)super.y(y);
    }

    @Override
    public FramedShowcaseBuilder y(String y) {
        return (FramedShowcaseBuilder)super.y(y);
    }

    public FramedShowcaseBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public FramedShowcaseBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    public FramedShowcaseBuilder scale(float scale) {
        this.scale = Either.left(scale);
        return this;
    }

    public FramedShowcaseBuilder scale(String scale) {
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
