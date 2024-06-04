package net.favouriteless.modopedia.api;

import net.minecraft.client.gui.GuiGraphics;

public interface Component {

    void init();

    void render(GuiGraphics graphics, int xMouse, int yMouse, float partialTicks);

}
