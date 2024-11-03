package net.favouriteless.modopedia.client.screens;

import net.favouriteless.modopedia.api.BookRegistry;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.page_components.BookRenderContext;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

public abstract class BookScreen extends Screen implements BookRenderContext {

    protected final ResourceLocation bookId;
    protected final Book book;
    protected final ResourceLocation texture;
    protected final BookScreen lastScreen;

    protected int ticks = 0;
    protected int texWidth;
    protected int texHeight;
    protected int leftPos = 0;
    protected int topPos = 0;

    public BookScreen(Book book, BookScreen lastScreen) {
        super(Component.literal(book.getTitle()));
        this.bookId = BookRegistry.get().getId(book);
        this.book = book;
        this.texture = book.getTexture();
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        super.init();
        this.leftPos = (width - texWidth) / 2;
        this.topPos = (height - texHeight) / 2;
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.renderTransparentBackground(graphics);
        graphics.blit(texture, leftPos, topPos, 0, 0, texWidth, texHeight, 512, 512);
    }

    @Override
    public void tick() {
        ticks++;
    }

    @Override
    public Book getBook() {
        return book;
    }

    @Override
    public BookScreen getScreen() {
        return this;
    }

    @Override
    public Style getStyle() {
        return Style.EMPTY.withFont(book.getFont()).withColor(book.getTextColour());
    }

    @Override
    public boolean isHovered(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX > x && mouseY > y && mouseX < x+width && mouseY < y+height;
    }

    @Override
    public ResourceLocation getBookTexture() {
        return book.getTexture();
    }

    @Override
    public int getTicks() {
        return ticks;
    }

}
