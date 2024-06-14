package net.favouriteless.modopedia.books;

import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.Category;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.util.BookUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookImpl implements Book {

    public final ResourceLocation id;
    public final ResourceLocation type;
    public final Component title;
    public final Component subtitle;
    public String landingText;
    public final ResourceLocation defaultTexture;
    public final ResourceLocation itemModel;

    public final List<Category> categories = new ArrayList<>();
    public final List<Entry> entries = new ArrayList<>();

    public BookImpl(ResourceLocation id, ResourceLocation type, Component title, Component subtitle,
                    ResourceLocation defaultTexture, ResourceLocation itemModel) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.subtitle = subtitle;
        this.defaultTexture = defaultTexture;
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
    public Component getTitle() {
        return title;
    }

    @Nullable
    @Override
    public Component getSubtitle() {
        return subtitle;
    }

    @Nullable
    @Override
    public String getLandingText() {
        return landingText;
    }

    @Override
    public ResourceLocation getDefaultTexture() {
        return defaultTexture;
    }

    @Override
    public ResourceLocation getItemModelLocation() {
        return itemModel;
    }

    @Override
    public List<Category> getCategories() {
        return categories;
    }

    @Nullable
    @Override
    public Category getCategory(String id) {
        return BookUtils.findCategory(id, categories);
    }

    @Override
    public List<Entry> getEntries() {
        return entries;
    }

    @Nullable
    @Override
    public Entry getEntry(String id) {
        return BookUtils.findEntry(id, entries);
    }

    public BookImpl addCategory(Category... categories) {
        Collections.addAll(this.categories, categories);
        return this;
    }

    public BookImpl addEntry(Entry... entries) {
        Collections.addAll(this.entries, entries);
        return this;
    }

}
