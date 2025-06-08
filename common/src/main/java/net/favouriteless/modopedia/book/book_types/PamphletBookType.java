package net.favouriteless.modopedia.book.book_types;

import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.books.BookType;
import net.favouriteless.modopedia.api.books.Category;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.client.screens.books.CategoryScreen;
import net.favouriteless.modopedia.client.screens.books.EntryScreen;
import net.favouriteless.modopedia.client.screens.books.PamphletLandingScreen;
import net.minecraft.client.gui.screens.Screen;

public class PamphletBookType implements BookType {

    @Override
    public Screen openLandingScreen(Book book, String langCode, LocalisedBookContent content) {
        Category cat = getCategory(content);
        return cat != null ? new PamphletLandingScreen(book, langCode, content, cat) : null;
    }

    @Override
    public Screen openCategoryScreen(Book book, String langCode, LocalisedBookContent content, String category) {
        Category cat = content.getCategory(category);
        return cat != null ? new CategoryScreen(book, langCode, content, cat) : null;
    }

    @Override
    public Screen openEntryScreen(Book book, String langCode, LocalisedBookContent content, String entry) {
        Entry ent = content.getEntry(entry);
        return ent != null ? new EntryScreen(book, langCode, content, ent) : null;
    }

    public static Category getCategory(LocalisedBookContent content) {
        for(String id : content.getCategoryIds()) {
            Category c = content.getCategory(id);
            if(c.getDisplayOnFrontPage())
                return c;
        }
        return null;
    }

}
