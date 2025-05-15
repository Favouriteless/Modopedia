package net.favouriteless.modopedia.api.books;

import net.minecraft.client.gui.screens.Screen;

/**
 * A BookScreenFactory represents a single "type" of book by providing the methods used to open its GUIs. e.g
 * "modopedia:classic".
 */
public interface BookType {

    /**
     * @return The "Landing Screen" of a book of this type. The first page viewed when a book is opene for the first
     * time. It acts as the "home page" of a book.
     */
    Screen openLandingScreen(Book book, BookContent content);

    /**
     * @return A "category" screen for a given category location. Traditionally this shows a list of entries in the
     * category.
     */
    Screen openCategoryScreen(Book book, BookContent content, String category);

    /**
     * @return An "entry" screen for a given category location. Traditionally this just renders each of the pages with
     * the components on them.
     */
    Screen openEntryScreen(Book book, BookContent content, String entry);

}
