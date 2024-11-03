package net.favouriteless.modopedia.api.books.page_components;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public interface PageComponentEventHandler extends PageEventListener {

    List<? extends PageEventListener> children();

    boolean isDragging();

    void setDragging(boolean isDragging);

    @Nullable PageEventListener getFocused();

    void setFocused(@Nullable PageEventListener focused);

    default Optional<PageEventListener> getChildAt(double mouseX, double mouseY) {
        for(PageEventListener widget : children()) {
            if(widget.isMouseOver(mouseX, mouseY))
                return Optional.of(widget);
        }
        return Optional.empty();
    }

    @Override
    default boolean mouseClicked(BookRenderContext context, double mouseX, double mouseY, int button) {
        for(PageEventListener child : children()) {
            if(child.mouseClicked(context, mouseX, mouseY, button)) {
                setFocused(child);
                if(button == 0)
                    this.setDragging(true);

                return true;
            }
        }
        return false;
    }

    @Override
    default boolean mouseReleased(BookRenderContext context, double mouseX, double mouseY, int button) {
        if(button == 0 && isDragging()) {
            setDragging(false);
            if(getFocused() != null)
                return getFocused().mouseReleased(context, mouseX, mouseY, button);
        }
        return this.getChildAt(mouseX, mouseY).filter(child -> child.mouseReleased(context, mouseX, mouseY, button)).isPresent();
    }

    @Override
    default boolean mouseDragged(BookRenderContext context, double mouseX, double mouseY, int button, double dragX, double dragY) {
        return getFocused() != null && isDragging() && button == 0 &&
                getFocused().mouseDragged(context, mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    default boolean mouseScrolled(BookRenderContext context, double mouseX, double mouseY, double scrollX, double scrollY) {
        return this.getChildAt(mouseX, mouseY).filter(child -> child.mouseScrolled(context, mouseX, mouseY, scrollX, scrollY)).isPresent();
    }

    @Override
    default boolean keyPressed(BookRenderContext context, int keyCode, int scanCode, int modifiers) {
        return getFocused() != null && getFocused().keyPressed(context, keyCode, scanCode, modifiers);
    }

    @Override
    default boolean keyReleased(BookRenderContext context, int keyCode, int scanCode, int modifiers) {
        return getFocused() != null && getFocused().keyReleased(context, keyCode, scanCode, modifiers);
    }

    @Override
    default boolean charTyped(BookRenderContext context, char codePoint, int modifiers) {
        return getFocused() != null && getFocused().charTyped(context, codePoint, modifiers);
    }

}
