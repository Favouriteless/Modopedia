package net.favouriteless.modopedia.api.books;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

/**
 * Links a {@link Book} to various rendering information. (You only need to care about this if you're creating your own
 * screens)
 */
public record BookTexture(ResourceLocation location, int width, int height, int texWidth, int texHeight, // Texture info
                          List<Rectangle> pages, FixedRectangle titleBacker, // Common elements
                          FixedRectangle left, FixedRectangle right, FixedRectangle back, FixedRectangle refresh,// Navigation buttons
                          Rectangle separator, Rectangle smallFrame, Rectangle mediumFrame, // Dynamic elements
                          Rectangle largeFrame, Rectangle craftingFrame) {


    /**
     * Represents a rectangle on a texture. Usually used for dynamic widgets such as separators.
     */
    public record Rectangle(int u, int v, int width, int height) {

        public static final Rectangle ZERO = new Rectangle(0, 0, 0, 0);

    }

    /**
     * Represents a rectangle on a texture which gets placed at a specific position. Usually used for fixed widgets
     * such as the navigation buttons.
     */
    public record FixedRectangle(int x, int y, int u, int v, int width, int height) {

        public static final FixedRectangle ZERO = new FixedRectangle(0, 0, 0, 0, 0, 0);

    }

}
