package net.favouriteless.modopedia.book.components;

import net.favouriteless.modopedia.api.Variable.Lookup;
import net.favouriteless.modopedia.api.books.BookRenderContext;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class TextPageComponent extends PageComponent {

    protected Component text;
    protected int width = 100;

    @Override
    public void init(Lookup lookup) {
        super.init(lookup);
        text = Component.literal(lookup.get("text").asString());
        width = lookup.getOrDefault("width", 100).asInt();
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int xMouse, int yMouse, float partialTicks) {
        graphics.drawString(Minecraft.getInstance().font, text, x, y, 0, false);
    }

}
