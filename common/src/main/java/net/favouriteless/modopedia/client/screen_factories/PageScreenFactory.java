package net.favouriteless.modopedia.client.screen_factories;

import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.book.BookScreenFactory;
import net.favouriteless.modopedia.client.screens.books.BookScreen;
import net.favouriteless.modopedia.client.screens.books.EntryScreen;
import net.favouriteless.modopedia.common.book_types.PageBookType;

public class PageScreenFactory implements BookScreenFactory<PageBookType> {

    @Override
    public BookScreen<?> openLandingScreen(Book book, PageBookType type, String language, LocalisedBookContent content, BookScreen<?> lastScreen) {
        return getFirstEntry(book, type, language, content, lastScreen);
    }

    @Override
    public BookScreen<?> openCategoryScreen(Book book, PageBookType type, String language, LocalisedBookContent content, String category, BookScreen<?> lastScreen) {
        return getFirstEntry(book, type, language, content, lastScreen);
    }

    @Override
    public BookScreen<?> openEntryScreen(Book book, PageBookType type, String language, LocalisedBookContent content, String entry, BookScreen<?> lastScreen) {
        return getFirstEntry(book, type, language, content, lastScreen);
    }

    public BookScreen<?> getFirstEntry(Book book, PageBookType type, String language, LocalisedBookContent content, BookScreen<?> lastScreen) {
        return content.getEntryIds().stream().findFirst()
                .map(content::getEntry)
                .map(entry -> new EntryScreen<>(book, type, language, content, entry, lastScreen))
                .orElse(null);
    }

}
