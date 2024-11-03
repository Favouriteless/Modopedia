package net.favouriteless.modopedia.api.books.page_components;

import net.minecraft.client.gui.GuiGraphics;

public interface PageRenderable {

    void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick);

}
