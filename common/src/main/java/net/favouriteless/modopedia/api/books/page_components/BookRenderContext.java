package net.favouriteless.modopedia.api.books.page_components;

import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.client.screens.BookScreen;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

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
     * Check if the mouse is hovering within a rectangle.
     *
     * @return true if mouse is within bounds.
     */
    boolean isHovered(double mouseX, double mouseY, int x, int y, int width, int height);

    /**
     * @return {@link ResourceLocation} pointing to this category's book texture.
     */
    ResourceLocation getBookTexture();

    /**
     * @return The number of ticks the current book has been open for.
     */
    int getTicks();

}
