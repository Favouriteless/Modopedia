package net.favouriteless.modopedia.api.books;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

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
     * Render the {@link ItemStack}s in an {@link Ingredient} in sequence, with a tooltip if the mouse is hovering it.
     */
    void renderIngredient(GuiGraphics graphics, int x, int y, int mouseX, int mouseY, Ingredient ingredient);

    /**
     * Render the {@link ItemStack}s in an {@link Ingredient} in sequence, with no tooltip.
     */
    void renderIngredientNoTooltip(GuiGraphics graphics, int x, int y, int mouseX, int mouseY, Ingredient ingredient);

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
