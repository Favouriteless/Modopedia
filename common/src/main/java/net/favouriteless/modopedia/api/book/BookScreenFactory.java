package net.favouriteless.modopedia.api.book;

import net.favouriteless.modopedia.api.book.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.common.book_types.ClassicBookType;
import net.favouriteless.modopedia.client.screens.books.BookScreen;
import org.jetbrains.annotations.Nullable;

/**
 * <p>
 *     A BookScreenFactory provides methods used to open {@link BookScreen}s using a given {@link Book} and {@link BookType}.
 * </p>
 * <p>
 *     See {@link ClassicBookType} for an example.
 * </p>
 */
public interface BookScreenFactory<T extends BookType> {

    /**
     * @return The "Landing Screen" of a book of this type. The first page viewed when a book is opened for the first
     * time. It acts as the "home page" of a book.
     */
    @Nullable BookScreen openLandingScreen(T type, Book book, String langCode, LocalisedBookContent content, BookScreen lastScreen);

    /**
     * @return A "entry" screen for a given entry location. Traditionally this shows a list of entries in the
     * entry.
     */
    @Nullable BookScreen openCategoryScreen(T type, Book book, String langCode, LocalisedBookContent content, String category, BookScreen lastScreen);

    /**
     * @return An "entry" screen for a given entry location. Traditionally this just renders each of the pages with
     * the components on them.
     */
    @Nullable BookScreen openEntryScreen(T type, Book book, String langCode, LocalisedBookContent content, String entry, BookScreen lastScreen);

}
