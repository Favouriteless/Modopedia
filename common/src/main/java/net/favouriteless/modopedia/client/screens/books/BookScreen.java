package net.favouriteless.modopedia.client.screens.books;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.BookRegistry;
import net.favouriteless.modopedia.api.BookScreenCache;
import net.favouriteless.modopedia.api.BookTextureRegistry;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.books.BookTexture;
import net.favouriteless.modopedia.api.books.BookTexture.PageDetails;
import net.favouriteless.modopedia.api.books.page_components.BookRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

public abstract class BookScreen extends Screen implements BookRenderContext {

    protected final ResourceLocation bookId;
    protected final Book book;
    protected final String langCode;
    protected final LocalisedBookContent content;
    protected final BookTexture texture;
    protected final BookScreen lastScreen;

    protected int ticks = 0;
    protected int leftPos = 0;
    protected int topPos = 0;

    public BookScreen(Book book, String langCode, LocalisedBookContent content, BookScreen lastScreen, Component title) {
        super(title);
        this.bookId = BookRegistry.get().getId(book);
        this.book = book;
        this.langCode = langCode;
        this.content = content;
        this.texture = BookTextureRegistry.get().getTexture(book.getTexture());
        this.lastScreen = lastScreen;
        BookScreenCache.get().setLastScreen(bookId, langCode, this);
    }

    @Override
    protected void init() {
        super.init();
        if(texture == null) {
            Modopedia.LOG.error("Could not find book texture: {}", book.getTexture().toString());
            Minecraft.getInstance().setScreen(null); // Force close the screen if using an unregistered texture.
            return;
        }

        this.leftPos = (width - texture.width()) / 2;
        this.topPos = (height - texture.height()) / 2;
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.renderTransparentBackground(graphics);
        graphics.blit(texture.location(), leftPos, topPos, 0, 0,
                texture.width(), texture.height(), texture.texWidth(), texture.texHeight());
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
    public BookTexture getBookTexture() {
        return texture;
    }

    @Override
    public boolean isHovered(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX > x && mouseY > y && mouseX < x+width && mouseY < y+height;
    }
    @Override
    public int getTicks() {
        return ticks;
    }

    /**
     * @return The height of the shortest page on this screen's BookTexture, not including the first page. Relevant for
     * side scrolling behaviour.
     */
    protected int getShortestHeight(int startIndex) {
        int smallest = Integer.MAX_VALUE;
        for(int i = startIndex; i < texture.pages().size(); i++) {
            PageDetails page = texture.pages().get(i);
            if(page.height() < smallest)
                smallest = page.height();
        }
        return smallest;
    }

    /**
     * @return The width of the thinnest page on this screen's BookTexture, not including the first page. Relevant for
     * side scrolling behaviour.
     */
    protected int getThinnestWidth(int startIndex) {
        int smallest = Integer.MAX_VALUE;
        for(int i = startIndex; i < texture.pages().size(); i++) {
            PageDetails page = texture.pages().get(i);
            if(page.height() < smallest)
                smallest = page.height();
        }
        return smallest;
    }

}
