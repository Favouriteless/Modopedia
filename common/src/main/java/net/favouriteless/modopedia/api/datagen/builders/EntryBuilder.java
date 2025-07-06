package net.favouriteless.modopedia.api.datagen.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.api.book.Entry;
import net.favouriteless.modopedia.api.datagen.EntryOutput;
import net.favouriteless.modopedia.book.EntryImpl;
import net.favouriteless.modopedia.datagen.builders.BookContentBuilder;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntryBuilder extends BookContentBuilder {

    private final String id;
    private final String title;

    private ItemStack iconStack = EntryImpl.DEFAULT_ICON.get();
    private final List<ResourceLocation> assignedItems = new ArrayList<>();
    private ResourceLocation advancement;

    private final List<PageBuilder> pages = new ArrayList<>();

    private EntryBuilder(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public static EntryBuilder of(String id, String title) {
        return new EntryBuilder(id, title);
    }

    public EntryBuilder icon(ItemStack icon) {
        this.iconStack = icon;
        return this;
    }

    public EntryBuilder advancement(ResourceLocation advancement) {
        this.advancement = advancement;
        return this;
    }

    public EntryBuilder assignedItems(Item... items) {
        this.assignedItems.addAll(Arrays.stream(items).map(BuiltInRegistries.ITEM::getKey).toList());
        return this;
    }

    public EntryBuilder pages(PageBuilder... pages) {
        this.pages.addAll(Arrays.asList(pages));
        return this;
    }

    public EntryBuilder page(PageComponentBuilder... components) {
        this.pages.add(PageBuilder.of().components(components));
        return this;
    }

    @Override
    public JsonElement build(RegistryOps<JsonElement> ops) {
        JsonObject json = Entry.codec().encodeStart(ops, new EntryImpl(title, iconStack, assignedItems, advancement)).getOrThrow().getAsJsonObject();
        JsonArray pages = new JsonArray();
        for(PageBuilder builder : this.pages) {
            pages.add(builder.build(ops));
        }
        json.add("pages", pages);
        return json;
    }

    public void build(EntryOutput output) {
        output.accept(id, build(output.registryOps(JsonOps.INSTANCE)));
    }

}
