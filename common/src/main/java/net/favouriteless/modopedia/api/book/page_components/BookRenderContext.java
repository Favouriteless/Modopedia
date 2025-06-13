package net.favouriteless.modopedia.api.book.page_components;

import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookTexture;
import net.favouriteless.modopedia.client.screens.books.BookScreen;
import net.minecraft.network.chat.Style;

/**
 * Provides rendering and input handling context for {@link PageRenderable}s and {@link PageEventListener}s.
 */
public interface BookRenderContext {

    /**
     * @return The {@link Book} currently opened.
     */
    Book getBook();

    /**
     * @return The screen currently being rendered.
     */
    BookScreen getScreen();

    /**
     * @return Default style for the current book.
     */
    Style getStyle();

    /**
     * @return Default header style for the current book.
     */
    Style getHeaderStyle();

    /**
     * @return The {@link BookTexture} being used by the current Screen.
     */
    BookTexture getBookTexture();

    /**
     * Check if the mouse is hovering within a rectangle.
     *
     * @return true if mouse is within bounds.
     */
    boolean isHovered(double mouseX, double mouseY, int x, int y, int width, int height);

    /**
     * @return The number of ticks the current book has been open for.
     */
    int getTicks();

}
