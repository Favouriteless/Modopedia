package net.favouriteless.modopedia.client.book_types;

import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.books.BookType;
import net.favouriteless.modopedia.api.books.Category;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.client.screens.books.CategoryScreen;
import net.favouriteless.modopedia.client.screens.books.EntryScreen;
import net.minecraft.client.gui.screens.Screen;

public class PamphletBookType implements BookType {

    @Override
    public Screen openLandingScreen(Book book, LocalisedBookContent content) {
        Category cat = null;
        for(String id : content.getCategoryIds()) {
            Category c = content.getCategory(id);
            if(c.getDisplayOnFrontPage()) {
                cat = c;
                break;
            }
        }

        return cat != null ? new CategoryScreen(book, content, cat) : null;
    }

    @Override
    public Screen openCategoryScreen(Book book, LocalisedBookContent content, String category) {
        Category cat = content.getCategory(category);
        return cat != null ? new CategoryScreen(book, content, cat) : null;
    }

    @Override
    public Screen openEntryScreen(Book book, LocalisedBookContent content, String entry) {
        Entry ent = content.getEntry(entry);
        return ent != null ? new EntryScreen(book, content, ent) : null;
    }

}
