package net.favouriteless.modopedia.client.screen_factories;

import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.book.BookScreenFactory;
import net.favouriteless.modopedia.api.book.Category;
import net.favouriteless.modopedia.api.book.Entry;
import net.favouriteless.modopedia.client.screens.books.BookScreen;
import net.favouriteless.modopedia.client.screens.books.CategoryScreen;
import net.favouriteless.modopedia.client.screens.books.ClassicLandingScreen;
import net.favouriteless.modopedia.client.screens.books.EntryScreen;
import net.favouriteless.modopedia.common.book_types.ClassicBookType;

public class ClassicScreenFactory implements BookScreenFactory<ClassicBookType> {

    @Override
    public BookScreen openLandingScreen(ClassicBookType type, Book book, String langCode, LocalisedBookContent content, BookScreen lastScreen) {
        return new ClassicLandingScreen(book, langCode, content);
    }

    @Override
    public BookScreen openCategoryScreen(ClassicBookType type, Book book, String langCode, LocalisedBookContent content, String category, BookScreen lastScreen) {
        Category cat = content.getCategory(category);
        return cat != null ? new CategoryScreen(book, langCode, content, cat, lastScreen) : lastScreen;
    }

    @Override
    public BookScreen openEntryScreen(ClassicBookType type, Book book, String langCode, LocalisedBookContent content, String entry, BookScreen lastScreen) {
        Entry ent = content.getEntry(entry);
        return ent != null ? new EntryScreen(book, langCode, content, ent, lastScreen) : null;
    }

}
