package net.favouriteless.modopedia.book;

import com.google.gson.JsonObject;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.Category;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.util.ItemUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CategoryImpl implements Category {

    private final Book book;
    private final String id;
    private final String title;
    private final String subtitle;
    private final Component description;
    private final ItemStack iconStack;
    private final ResourceLocation defaultTexture;

    private final List<String> entries = new ArrayList<>();
    private final List<String> children = new ArrayList<>();


    public CategoryImpl(Book book, String id, String title, String subtitle, Component description, ItemStack iconStack,
                        ResourceLocation defaultTexture) {
        this.book = book;
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.defaultTexture = defaultTexture;
        this.iconStack = iconStack;
    }


    @Override
    public Book getBook() {
        return book;
    }

    @Override
    public String getId() {
        return id;
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
    public Component getDescription() {
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
    public List<String> getEntries() {
        return entries;
    }

    @Override
    public List<String> getChildren() {
        return children;
    }

    public CategoryImpl addChild(Category... children) {
        for(Category category : children) {
            this.children.add(category.getId());
        }
        return this;
    }

    public CategoryImpl addEntry(Entry... entries) {
        for(Entry entry : entries) {
            this.entries.add(entry.getId());
        }        return this;
    }

    public static CategoryImpl fromJson(Book book, String id, JsonObject jsonObject) {
        String title = jsonObject.get("title").getAsString();
        String subtitle = jsonObject.has("subtitle") ? jsonObject.get("subtitle").getAsString() : null;
        Component description = jsonObject.has("description") ? Component.literal(jsonObject.get("description").getAsString()) : null;
        ItemStack iconStack = jsonObject.has("icon") ? ItemUtils.itemFromJson(jsonObject.get("icon")) : ItemStack.EMPTY;
        ResourceLocation texture = jsonObject.has("texture") ? ResourceLocation.parse(jsonObject.get("texture").getAsString()) : null;

        return new CategoryImpl(book, id, title, subtitle, description, iconStack, texture);
    }


}
