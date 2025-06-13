package net.favouriteless.modopedia.book.book_types;

import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.books.BookType;
import net.favouriteless.modopedia.api.books.Category;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.client.screens.books.BookScreen;
import net.favouriteless.modopedia.client.screens.books.CategoryScreen;
import net.favouriteless.modopedia.client.screens.books.ClassicLandingScreen;
import net.favouriteless.modopedia.client.screens.books.EntryScreen;
import net.minecraft.client.gui.screens.Screen;

public class ClassicBookType implements BookType {

    @Override
    public BookScreen openLandingScreen(Book book, String langCode, LocalisedBookContent content, BookScreen lastScreen) {
        return new ClassicLandingScreen(book, langCode, content);
    }

    @Override
    public BookScreen openCategoryScreen(Book book, String langCode, LocalisedBookContent content, String category, BookScreen lastScreen) {
        Category cat = content.getCategory(category);
        return cat != null ? new CategoryScreen(book, langCode, content, cat, lastScreen) : lastScreen;
    }

    @Override
    public BookScreen openEntryScreen(Book book, String langCode, LocalisedBookContent content, String entry, BookScreen lastScreen) {
        Entry ent = content.getEntry(entry);
        return ent != null ? new EntryScreen(book, langCode, content, ent, lastScreen) : null;
    }

}
