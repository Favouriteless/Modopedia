package net.favouriteless.modopedia.book;

import com.google.gson.JsonObject;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.Category;
import net.favouriteless.modopedia.api.books.Entry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class BookImpl implements Book {

    private final ResourceLocation id;
    private final ResourceLocation type;
    private final String title;
    private final String subtitle;
    private final Component landingText;
    private final ResourceLocation texture;
    private final ResourceLocation itemModel;

    private final Map<String, Category> categories = new HashMap<>();
    private final Map<String, Entry> entries = new HashMap<>();

    public BookImpl(ResourceLocation id, ResourceLocation type, String title, String subtitle, Component landingText,
                    ResourceLocation texture, ResourceLocation itemModel) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.subtitle = subtitle;
        this.landingText = landingText;
        this.texture = texture;
        this.itemModel = itemModel;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public ResourceLocation getType() {
        return type;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public String getSubtitle() {
        return subtitle;
    }

    @Nullable
    @Override
    public Component getLandingText() {
        return landingText;
    }

    @Override
    public ResourceLocation getTexture() {
        return texture;
    }

    @Override
    public ResourceLocation getItemModelLocation() {
        return itemModel;
    }

    @Nullable
    @Override
    public Category getCategory(String id) {
        return categories.get(id);
    }

    @Nullable
    @Override
    public Entry getEntry(String id) {
        return entries.get(id);
    }

    public BookImpl addCategory(Category... categories) {
        for(Category category : categories) {
            this.categories.put(category.getId(), category);
        }
        return this;
    }

    public BookImpl addEntry(Entry... entries) {
        for(Entry entry : entries) {
            this.entries.put(entry.getId(), entry);
        }
        return this;
    }

    public static BookImpl fromJson(ResourceLocation id, JsonObject jsonObject) {
        ResourceLocation type = jsonObject.has("type") ? ResourceLocation.parse(jsonObject.get("type").getAsString()) : Modopedia.id("classic");
        String title = jsonObject.get("title").getAsString();
        String subtitle = jsonObject.has("subtitle") ? jsonObject.get("subtitle").getAsString() : null;
        Component landingText = jsonObject.has("landingText") ? Component.literal(jsonObject.get("landingText").getAsString()) : null;
        ResourceLocation texture = jsonObject.has("texture") ? ResourceLocation.parse(jsonObject.get("texture").getAsString()) : Modopedia.id("book/default.png");
        ResourceLocation itemModel = jsonObject.has("itemModel") ? ResourceLocation.parse(jsonObject.get("itemModel").getAsString()) : Modopedia.id("item/book_default");

        return new BookImpl(id, type, title, subtitle, landingText, texture, itemModel);
    }

}
