package net.favouriteless.modopedia.book;

import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.Category;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.util.BookUtils;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookImpl implements Book {

    public final ResourceLocation id;
    public final ResourceLocation type;
    public final String title;
    public final String subtitle;
    public String landingText;
    public final ResourceLocation texture;
    public final ResourceLocation itemModel;

    public final List<Category> categories = new ArrayList<>();
    public final List<Entry> entries = new ArrayList<>();

    public BookImpl(ResourceLocation id, ResourceLocation type, String title, String subtitle, ResourceLocation texture,
                    ResourceLocation itemModel) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.subtitle = subtitle;
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
    public String getLandingText() {
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
