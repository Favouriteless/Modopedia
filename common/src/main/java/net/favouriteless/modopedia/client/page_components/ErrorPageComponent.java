package net.favouriteless.modopedia.client.page_components;

import net.favouriteless.modopedia.api.books.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

/**
 * ErrorPageComponent is used to replace broken components when they fail to initialise. It is not part of the registry
 * and can be considered a special case.
 */
public class ErrorPageComponent extends PageComponent {

    public static final Component ERROR = Component.translatable("screen.modopedia.error");

    protected final Component error;

    public ErrorPageComponent(String error) {
        this.error = Component.literal(error);
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick) {
        Font font = Minecraft.getInstance().font;
        graphics.drawString(Minecraft.getInstance().font, ERROR, x, y, 0xFF0000);

        if(context.isHovered(mouseX, mouseY, x, y, font.width(ERROR), font.lineHeight))
            graphics.renderTooltip(font, error, mouseX, mouseY);
    }

}
