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
import org.jetbrains.annotations.Nullable;

public class ClassicScreenFactory implements BookScreenFactory<ClassicBookType> {

    @Override
    public @Nullable BookScreen<?> openLandingScreen(Book book, ClassicBookType type, String language, LocalisedBookContent content, BookScreen<?> lastScreen) {
        return new ClassicLandingScreen<>(book, type, language, content, lastScreen);
    }

    @Override
    public @Nullable BookScreen<?> openCategoryScreen(Book book, ClassicBookType type, String language, LocalisedBookContent content, String category, BookScreen<?> lastScreen) {
        Category cat = content.getCategory(category);
        return cat != null ? new CategoryScreen<>(book, type, language, content, cat, lastScreen) : lastScreen;
    }

    @Override
    public @Nullable BookScreen<?> openEntryScreen(Book book, ClassicBookType type, String language, LocalisedBookContent content, String entry, BookScreen<?> lastScreen) {
        Entry ent = content.getEntry(entry);
        return ent != null ? new EntryScreen<>(book, type, language, content, ent, lastScreen) : null;
    }

}
