package net.favouriteless.modopedia.client.screens.widgets;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;

public class HoverableImageButton extends Button {

    private final ResourceLocation texture;
    private final int u;
    private final int v;
    private final int texWidth;
    private final int texHeight;

    public HoverableImageButton(ResourceLocation texture, int x, int y, int width, int height, int u, int v, int texWidth, int texHeight, OnPress onPress) {
        super(x, y, width, height, CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
        this.texture = texture;
        this.u = u;
        this.v = v;
        this.texWidth = texWidth;
        this.texHeight = texHeight;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        int v = isHovered() ? this.v + height : this.v;
        graphics.blit(texture, getX(), getY(), u, v, width, height, texWidth, texHeight);
    }

}
