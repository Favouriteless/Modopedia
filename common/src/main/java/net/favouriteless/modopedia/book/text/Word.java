package net.favouriteless.modopedia.book.text;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

/**
 * Each word is represented in its own class to allow for the layouter to wrap them correctly.
 */
public class Word {

    private final Component text;

    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public Word(Component text, int x, int y, int width, int height) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(GuiGraphics graphics, Font font, int mouseX, int mouseY) {
        Style style = text.getStyle();
        graphics.drawString(font, text, x, y, style.getColor() != null ? style.getColor().getValue() : 0x000000); // Defaults to black if no colour is present.

        if(isHovered(mouseX, mouseY))
            graphics.renderComponentHoverEffect(font, style, mouseX, mouseY);
    }

    private boolean isHovered(double mouseX, double mouseY) {
        return mouseX > x && mouseX < x+width && mouseY > y && mouseY < y+height;
    }
}
