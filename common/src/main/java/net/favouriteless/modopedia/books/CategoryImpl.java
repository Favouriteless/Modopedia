package net.favouriteless.modopedia.books;

import net.favouriteless.modopedia.api.books.Category;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.util.BookUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoryImpl implements Category {

    private final String id;
    private final Component title;
    private final Component subtitle;
    private final String description;
    private final ItemStack iconStack;
    private final ResourceLocation defaultTexture;

    private final List<Entry> entries = new ArrayList<>();
    private final List<Category> children = new ArrayList<>();


    public CategoryImpl(String id, Component title, Component subtitle, String description, ItemStack iconStack,
                        ResourceLocation defaultTexture) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.defaultTexture = defaultTexture;
        this.iconStack = iconStack;
    }


    @Override
    public String getId() {
        return id;
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
    public String getDescription() {
        return description;
    }

    @Override
    public ItemStack getIcon() {
        return iconStack;
    }

    @Nullable
    @Override
    public ResourceLocation getDefaultTexture() {
        return defaultTexture;
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

    @Override
    public List<Category> getChildren() {
        return children;
    }

    @Nullable
    @Override
    public Category getChild(String id) {
        return BookUtils.findCategory(id, children);
    }

    public CategoryImpl addChild(Category... children) {
        Collections.addAll(this.children, children);
        return this;
    }

    public CategoryImpl addEntry(Entry... entries) {
        Collections.addAll(this.entries, entries);
        return this;
    }

}
