package net.favouriteless.modopedia.book;

import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.api.books.Page;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntryImpl implements Entry {

    private final Book book;
    private final String id;
    private final String title;
    private final ItemStack iconStack;

    private final List<Page> pages = new ArrayList<>();

    public EntryImpl(Book book, String id, String title, ItemStack iconStack) {
        this.book = book;
        this.id = id;
        this.title = title;
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
