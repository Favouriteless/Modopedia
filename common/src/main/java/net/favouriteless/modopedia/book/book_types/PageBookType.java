package net.favouriteless.modopedia.book.book_types;

import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.books.BookType;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.client.screens.books.BookScreen;
import net.favouriteless.modopedia.client.screens.books.EntryScreen;
import net.minecraft.client.gui.screens.Screen;

public class PageBookType implements BookType {

    @Override
    public BookScreen openLandingScreen(Book book, String langCode, LocalisedBookContent content, BookScreen lastScreen) {
        Entry entry = getFirstEntry(content);
        return entry != null ? new EntryScreen(book, langCode, content, entry, lastScreen) : null;
    }

    @Override
    public BookScreen openCategoryScreen(Book book, String langCode, LocalisedBookContent content, String category, BookScreen lastScreen) {
        Entry entry = getFirstEntry(content);
        return entry != null ? new EntryScreen(book, langCode, content, entry, lastScreen) : null;
    }

    @Override
    public BookScreen openEntryScreen(Book book, String langCode, LocalisedBookContent content, String entry, BookScreen lastScreen) {
        Entry e = getFirstEntry(content);
        return e != null ? new EntryScreen(book, langCode, content, e, lastScreen) : null;
    }

    public Entry getFirstEntry(LocalisedBookContent content) {
        return content.getEntry(content.getEntryIds().stream().findFirst().orElse(null));
    }

}
