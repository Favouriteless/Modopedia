package net.favouriteless.modopedia.api.books.page_components;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;

/**
 * An alternative to {@link Renderable} which allows the object to have access to the current {@link BookRenderContext}.
 */
public interface PageRenderable {

    default boolean shouldRender() {
        return true;
    }

    void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick);

    default void tick(BookRenderContext context) {}

}
