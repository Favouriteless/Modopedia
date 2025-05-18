package net.favouriteless.modopedia.api.books.page_components;

import net.minecraft.client.gui.components.events.GuiEventListener;

/**
 * An alternative to {@link GuiEventListener} which allows the object to have access to the current
 * {@link BookRenderContext}.
 */
public interface PageEventListener {

    long DOUBLE_CLICK_THRESHOLD_MS = 250L;

    default boolean mouseClicked(BookRenderContext context, double mouseX, double mouseY, int button) {
        return false;
    }

    default boolean mouseReleased(BookRenderContext context, double mouseX, double mouseY, int button) {
        return false;
    }

    default boolean mouseDragged(BookRenderContext context, double mouseX, double mouseY, int button, double dragX, double dragY) {
        return false;
    }

    default boolean mouseScrolled(BookRenderContext context, double mouseX, double mouseY, double scrollX, double scrollY) {
        return false;
    }

    default boolean keyPressed(BookRenderContext context, int keyCode, int scanCode, int modifiers) {
        return false;
    }

    default boolean keyReleased(BookRenderContext context, int keyCode, int scanCode, int modifiers) {
        return false;
    }

    default boolean charTyped(BookRenderContext context, char codePoint, int modifiers) {
        return false;
    }

    default boolean isMouseOver(double mouseX, double mouseY) {
        return false;
    }

}
