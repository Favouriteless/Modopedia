package net.favouriteless.modopedia.book.text;

import net.favouriteless.modopedia.api.books.page_components.BookRenderContext;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

/**
 * Each "chunk" is represented in its own class to allow for the {@link TextParser} to wrap them correctly. A chunk is
 * a set of characters on the same line which use the same styling.
 */
public class TextChunk {

    protected final Component text;

    protected int x;
    protected int y;
    protected final int width;
    protected final int height;

    public TextChunk(Component text, int x, int y, int width, int height) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(GuiGraphics graphics, BookRenderContext context, Font font, int mouseX, int mouseY) {
        graphics.drawString(font, text, x, y, 0x000000, false); // Defaults to black if no colour is present.

        if(isHovered(mouseX, mouseY))
            graphics.renderComponentHoverEffect(font, text.getStyle(), mouseX, mouseY);
    }

    public boolean pageClicked(BookRenderContext context, double mouseX, double mouseY, int button) {
        if(button == 0 && isHovered(mouseX, mouseY) && text.getStyle().getClickEvent() != null) {
            context.getScreen().handleComponentClicked(text.getStyle());
            return true;
        }
        return false;
    }

    private boolean isHovered(double mouseX, double mouseY) {
        return mouseX > x && mouseX < x+width && mouseY > y && mouseY < y+height;
    }

}
