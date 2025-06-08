package net.favouriteless.modopedia.book.page_components;

import net.favouriteless.modopedia.api.books.BookTexture;
import net.favouriteless.modopedia.api.books.BookTexture.Rectangle;
import net.favouriteless.modopedia.api.books.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.minecraft.client.gui.GuiGraphics;

import java.util.function.Function;

public class BookTextureWidgetPageComponent extends PageComponent {

    protected final Function<BookTexture, Rectangle> rectangleSupplier;


    public BookTextureWidgetPageComponent(Function<BookTexture, Rectangle> rectangleSupplier) {
        this.rectangleSupplier = rectangleSupplier;
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick) {
        BookTexture tex = context.getBookTexture();
        Rectangle rec = rectangleSupplier.apply(context.getBookTexture());
        graphics.blit(tex.location(), x, y, rec.width(), rec.height(), rec.u(), rec.v(), rec.width(), rec.height(), tex.texWidth(), tex.texHeight());
    }

}
