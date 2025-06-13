package net.favouriteless.modopedia.api.books;

import net.favouriteless.modopedia.api.books.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.client.screens.books.BookScreen;
import net.favouriteless.modopedia.client.screens.books.ClassicLandingScreen;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

/**
 * <p>
 *     A BookScreenFactory represents a single "type" of book by providing the methods used to open its Screens.
 * </p>
 * <p>
 *     See {@link ClassicLandingScreen} for an example.
 * </p>
 */
public interface BookType {

    /**
     * @return The "Landing Screen" of a book of this type. The first page viewed when a book is opened for the first
     * time. It acts as the "home page" of a book.
     */
    @Nullable BookScreen openLandingScreen(Book book, String langCode, LocalisedBookContent content, BookScreen lastScreen);

    /**
     * @return A "entry" screen for a given entry location. Traditionally this shows a list of entries in the
     * entry.
     */
    @Nullable BookScreen openCategoryScreen(Book book, String langCode, LocalisedBookContent content, String category, BookScreen lastScreen);

    /**
     * @return An "entry" screen for a given entry location. Traditionally this just renders each of the pages with
     * the components on them.
     */
    @Nullable BookScreen openEntryScreen(Book book, String langCode, LocalisedBookContent content, String entry, BookScreen lastScreen);

}
