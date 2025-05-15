package net.favouriteless.modopedia.book;

import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.Page;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.minecraft.world.level.Level;

import java.util.Collection;

public class PageImpl implements Page {

    private final PageComponentHolder holder;

    public PageImpl(PageComponentHolder holder) {
        this.holder = holder;
    }

    public void init(Book book, String entry, Level level) {
        holder.initComponents(book, entry, level);
    }

    @Override
    public Collection<PageComponent> getComponents() {
        return holder.getComponents();
    }

    // ------------------------------------ Below this point is non-API functions ------------------------------------

}
