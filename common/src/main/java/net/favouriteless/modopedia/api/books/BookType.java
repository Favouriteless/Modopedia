package net.favouriteless.modopedia.api.books;

import net.favouriteless.modopedia.api.books.BookContent.LocalisedBookContent;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

/**
 * A BookScreenFactory represents a single "type" of book by providing the methods used to open its GUIs. e.g
 * "modopedia:classic".
 */
public interface BookType {

    /**
     * @return The "Landing Screen" of a book of this type. The first page viewed when a book is opened for the first
     * time. It acts as the "home page" of a book.
     */
    @Nullable Screen openLandingScreen(Book book, String langCode, LocalisedBookContent content);

    /**
     * @return A "category" screen for a given category location. Traditionally this shows a list of entries in the
     * category.
     */
    @Nullable Screen openCategoryScreen(Book book, String langCode, LocalisedBookContent content, String category);

    /**
     * @return An "entry" screen for a given entry location. Traditionally this just renders each of the pages with
     * the components on them.
     */
    @Nullable Screen openEntryScreen(Book book, String langCode, LocalisedBookContent content, String entry);

}
