package net.favouriteless.modopedia.client.page_components;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.Lookup;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookTexture;
import net.favouriteless.modopedia.api.book.BookTexture.Rectangle;
import net.favouriteless.modopedia.api.book.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.book.page_components.PageComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class SeparatorPageComponent extends PageComponent {

    public static final ResourceLocation ID = Modopedia.id("separator");

    @Override
    public void init(Book book, Lookup lookup, Level level) {
        super.init(book, lookup, level);
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick) {
        BookTexture texture = context.getBookTexture();
        Rectangle separator = texture.widgets().get("separator");

        if(separator == null)
            return;

        int x = context.getBook().getLineWidth() / 2 - separator.width()/2;
        graphics.blit(texture.location(), x, y, separator.u(), separator.v(), separator.width(), separator.height(), texture.texWidth(), texture.texHeight());
    }

}
