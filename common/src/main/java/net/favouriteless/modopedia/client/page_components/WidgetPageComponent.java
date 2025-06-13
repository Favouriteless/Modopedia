package net.favouriteless.modopedia.client.page_components;

import net.favouriteless.modopedia.api.book.BookTexture;
import net.favouriteless.modopedia.api.book.BookTexture.Rectangle;
import net.favouriteless.modopedia.api.book.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.book.page_components.PageComponent;
import net.minecraft.client.gui.GuiGraphics;

public class WidgetPageComponent extends PageComponent {

    protected final String id;

    public WidgetPageComponent(String id) {
        this.id = id;
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick) {
        BookTexture tex = context.getBookTexture();
        Rectangle rec = tex.widgets().get(id);

        if(rec == null)
            return;

        graphics.blit(tex.location(), x, y, rec.width(), rec.height(), rec.u(), rec.v(), rec.width(), rec.height(), tex.texWidth(), tex.texHeight());
    }

}
