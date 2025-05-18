package net.favouriteless.modopedia.book.book_types;

import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.books.BookType;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.client.screens.books.EntryScreen;
import net.minecraft.client.gui.screens.Screen;

public class PageBookType implements BookType {

    @Override
    public Screen openLandingScreen(Book book, String langCode, LocalisedBookContent content) {
        Entry entry = getFirstEntry(content);
        return entry != null ? new EntryScreen(book, langCode, content, entry) : null;
    }

    @Override
    public Screen openCategoryScreen(Book book, String langCode, LocalisedBookContent content, String category) {
        Entry entry = getFirstEntry(content);
        return entry != null ? new EntryScreen(book, langCode, content, entry) : null;
    }

    @Override
    public Screen openEntryScreen(Book book, String langCode, LocalisedBookContent content, String entry) {
        Entry e = getFirstEntry(content);
        return e != null ? new EntryScreen(book, langCode, content, e) : null;
    }

    public Entry getFirstEntry(LocalisedBookContent content) {
        return content.getEntry(content.getEntryIds().stream().findFirst().orElse(null));
    }

}
