package net.favouriteless.modopedia.client.screen_factories;

import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.book.BookScreenFactory;
import net.favouriteless.modopedia.api.book.Category;
import net.favouriteless.modopedia.api.book.Entry;
import net.favouriteless.modopedia.client.screens.books.BookScreen;
import net.favouriteless.modopedia.client.screens.books.CategoryScreen;
import net.favouriteless.modopedia.client.screens.books.EntryScreen;
import net.favouriteless.modopedia.client.screens.books.PamphletLandingScreen;
import net.favouriteless.modopedia.common.book_types.PamphletBookType;

public class PamphletScreenFactory implements BookScreenFactory<PamphletBookType> {

    @Override
    public BookScreen openLandingScreen(PamphletBookType type, Book book, String langCode, LocalisedBookContent content, BookScreen lastScreen) {
        Category cat = getCategory(content);
        return cat != null ? new PamphletLandingScreen(book, langCode, content, cat, lastScreen) : null;
    }

    @Override
    public BookScreen openCategoryScreen(PamphletBookType type, Book book, String langCode, LocalisedBookContent content, String category, BookScreen lastScreen) {
        Category cat = content.getCategory(category);
        return cat != null ? new CategoryScreen(book, langCode, content, cat, lastScreen) : lastScreen;
    }

    @Override
    public BookScreen openEntryScreen(PamphletBookType type, Book book, String langCode, LocalisedBookContent content, String entry, BookScreen lastScreen) {
        Entry ent = content.getEntry(entry);
        return ent != null ? new EntryScreen(book, langCode, content, ent, lastScreen) : null;
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
