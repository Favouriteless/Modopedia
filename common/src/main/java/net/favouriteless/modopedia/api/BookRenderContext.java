package net.favouriteless.modopedia.api;

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
     * @return Font style for the current book.
     */
    Style getStyle();

    /**
     * Render an {@link ItemStack} to the screen, with a tooltip if the mouse is hovering it.
     */
    void renderItemStack(GuiGraphics graphics, int x, int y, int xMouse, int yMouse, ItemStack stack);

    /**
     * Render an {@link ItemStack} to the screen, with no tooltip.
     */
    void renderItemStackNoTooltip(GuiGraphics graphics, int x, int y, int xMouse, int yMouse, ItemStack stack);

    /**
     * Render the {@link ItemStack}s in an {@link Ingredient} in sequence, with a tooltip if the mouse is hovering it.
     */
    void renderIngredient(GuiGraphics graphics, int x, int y, int xMouse, int yMouse, Ingredient ingredient);

    /**
     * Render the {@link ItemStack}s in an {@link Ingredient} in sequence, with no tooltip.
     */
    void renderIngredientNoTooltip(GuiGraphics graphics, int x, int y, int xMouse, int yMouse, Ingredient ingredient);

    /**
     * Check if the mouse is hovering within a rectangle.
     *
     * @return true if mouse is within bounds.
     */
    boolean isHovered(int xMouse, int yMouse, int x, int y, int width, int height);

    /**
     * @return {@link ResourceLocation} pointing to this category's book texture.
     */
    ResourceLocation getBookTexture();

    /**
     * @return The number of ticks the current book has been open for.
     */
    int getTicks();

}
