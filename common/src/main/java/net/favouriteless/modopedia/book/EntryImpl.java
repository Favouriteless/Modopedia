package net.favouriteless.modopedia.book;

import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.api.books.Page;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntryImpl implements Entry {

    private final String id;
    private final Component title;
    private final ItemStack iconStack;

    private final List<Page> pages = new ArrayList<>();

    public EntryImpl(String id, Component title, ItemStack iconStack) {
        this.id = id;
        this.title = title;
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

    @Override
    public ItemStack getIcon() {
        return iconStack;
    }

    @Override
    public List<Page> getPages() {
        return pages;
    }

    public EntryImpl addPage(Page... pages) {
        Collections.addAll(this.pages, pages);
        return this;
    }

}
