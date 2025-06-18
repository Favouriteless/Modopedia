package net.favouriteless.modopedia.api.book;

import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Map;

/**
 * Links a {@link Book} to various rendering information. (You only need to care about this if you're creating your own
 * screens)
 */
public record BookTexture(ResourceLocation location, int width, int height, int texWidth, int texHeight, // Texture info
                          List<Rectangle> pages, FixedRectangle titleBacker, // Common elements
                          FixedRectangle left, FixedRectangle right, FixedRectangle back, FixedRectangle refresh, // Navigation buttons
                          Map<String, Rectangle> widgets) {
    
    /**
     * Represents a rectangle on a texture. Usually used for dynamic widgets such as separators.
     */
    public record Rectangle(int u, int v, int width, int height) {

        public static final Rectangle ZERO = Rectangle.of(0, 0, 0, 0);

        public static Rectangle of(int u, int v, int width, int height) {
            return new Rectangle(u, v, width, height);
        }

    }

    /**
     * Represents a rectangle on a texture which gets placed at a specific position. Usually used for fixed widgets
     * such as the navigation buttons.
     */
    public record FixedRectangle(int x, int y, int u, int v, int width, int height) {

        public static final FixedRectangle ZERO = FixedRectangle.of(0, 0, 0, 0, 0, 0);

        public static FixedRectangle of(int x, int y, int u, int v, int width, int height) {
            return new FixedRectangle(x, y, u, v, width, height);
        }

    }

}
