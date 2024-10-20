package net.favouriteless.modopedia.book;

import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.api.books.Page;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

public class EntryImpl implements Entry {

    private Book book; // This gets set after the Category is instantiated because we don't want it to be present in the persistent codec.
    
    private final String title;
    private final ItemStack iconStack;
    private final List<Page> pages;

    public EntryImpl(String title, ItemStack iconStack, List<Page> pages) {
        this.title = title;
        this.iconStack = iconStack;
        this.pages = pages;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    @Nullable
    public ItemStack getIcon() {
        return iconStack;
    }

    @Override
    public List<Page> getPages() {
        return pages;
    }
    
    // --------------------------------- Below this point is non-API functions ---------------------------------
    
}
