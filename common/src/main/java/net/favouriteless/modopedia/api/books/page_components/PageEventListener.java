package net.favouriteless.modopedia.api.books.page_components;

/**
 * Base interface for {@link PageComponent} widgets-- these differ to regular ones as they get {@link BookRenderContext}
 * passed in!
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
