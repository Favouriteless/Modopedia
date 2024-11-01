package net.favouriteless.modopedia.api.books.page_components;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

/**
 * Passed into {@link PageComponent#render(GuiGraphics, BookRenderContext, int, int, float)} and
 * {@link PageComponent#pageClicked(BookRenderContext, double, double, int)} to provide context for the action.
 */
public interface BookRenderContext {

    /**
     * @return The screen currently being rendered.
     */
    Screen getScreen();

    /**
     * @return Default style for the current book.
     */
    Style getStyle();

    /**
     * Check if the mouse is hovering within a rectangle.
     *
     * @return true if mouse is within bounds.
     */
    boolean isHovered(int mouseX, int mouseY, int x, int y, int width, int height);

    /**
     * @return {@link ResourceLocation} pointing to this category's book texture.
     */
    ResourceLocation getBookTexture();

    /**
     * @return The number of ticks the current book has been open for.
     */
    int getTicks();

}
