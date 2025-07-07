package net.favouriteless.modopedia.client.screen_factories;

import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.book.BookScreenFactory;
import net.favouriteless.modopedia.api.book.Entry;
import net.favouriteless.modopedia.client.screens.books.BookScreen;
import net.favouriteless.modopedia.client.screens.books.EntryScreen;
import net.favouriteless.modopedia.common.book_types.PageBookType;

public class PageScreenFactory implements BookScreenFactory<PageBookType> {

    @Override
    public BookScreen openLandingScreen(PageBookType type, Book book, String language, LocalisedBookContent content, BookScreen lastScreen) {
        Entry entry = getFirstEntry(content);
        return entry != null ? new EntryScreen(book, language, content, entry, lastScreen) : null;
    }

    @Override
    public BookScreen openCategoryScreen(PageBookType type, Book book, String language, LocalisedBookContent content, String category, BookScreen lastScreen) {
        Entry entry = getFirstEntry(content);
        return entry != null ? new EntryScreen(book, language, content, entry, lastScreen) : null;
    }

    @Override
    public BookScreen openEntryScreen(PageBookType type, Book book, String language, LocalisedBookContent content, String entry, BookScreen lastScreen) {
        Entry e = getFirstEntry(content);
        return e != null ? new EntryScreen(book, language, content, e, lastScreen) : null;
    }

    public Entry getFirstEntry(LocalisedBookContent content) {
        return content.getEntry(content.getEntryIds().stream().findFirst().orElse(null));
    }

}
