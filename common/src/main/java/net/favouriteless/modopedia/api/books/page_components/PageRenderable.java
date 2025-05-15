package net.favouriteless.modopedia.api.books.page_components;

import net.minecraft.client.gui.GuiGraphics;

public interface PageRenderable {

    default boolean shouldRender() {
        return true;
    }

    void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick);

}
