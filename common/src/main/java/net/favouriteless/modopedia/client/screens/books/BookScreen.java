package net.favouriteless.modopedia.client.screens.books;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.ScreenCache;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.book.BookTexture;
import net.favouriteless.modopedia.api.book.BookTexture.FixedRectangle;
import net.favouriteless.modopedia.api.book.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.registries.client.BookTextureRegistry;
import net.favouriteless.modopedia.api.registries.common.BookRegistry;
import net.favouriteless.modopedia.book.loading.BookContentLoader;
import net.favouriteless.modopedia.client.BookOpenHandler;
import net.favouriteless.modopedia.client.screens.widgets.BookImageButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

public abstract class BookScreen extends Screen implements BookRenderContext {

    protected final ResourceLocation bookId;
    protected final Book book;
    protected final String lang;
    protected final LocalisedBookContent content;
    protected final BookTexture texture;
    protected final BookScreen lastScreen;

    protected int ticks = 0;
    protected int leftPos = 0;
    protected int topPos = 0;

    public BookScreen(Book book, String lang, LocalisedBookContent content, BookScreen lastScreen, Component title) {
        super(title);
        this.bookId = BookRegistry.get().getId(book);
        this.book = book;
        this.lang = lang;
        this.content = content;
        this.texture = BookTextureRegistry.get().getTexture(book.getTexture());
        this.lastScreen = lastScreen;
        ScreenCache.get().setLastScreen(bookId, this.lang, this);
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

        if(minecraft.player == null || !minecraft.player.isCreative())
            return;

        FixedRectangle w = texture.refresh();
        addRenderableWidget(new BookImageButton(texture.location(), leftPos + w.x(), topPos + w.y(), w.width(), w.height(), w.u(), w.v(),
                texture.texWidth(), texture.texHeight(), b -> reloadBook(), SoundEvents.UI_BUTTON_CLICK));
    }

    protected void reloadBook() {
        minecraft.setScreen(null);
        BookContentLoader.reload(bookId);
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.renderTransparentBackground(graphics);
        graphics.blit(texture.location(), leftPos, topPos, 0, 0,
                texture.width(), texture.height(), texture.texWidth(), texture.texHeight());
    }

    protected void tryBackButton() {
        if(hasShiftDown() || lastScreen == null) {
            if(isTopLevel())
                return;
            ScreenCache.get().setLastScreen(bookId, lang, null);
            BookOpenHandler.tryOpenBook(bookId);
        }
        else {
            minecraft.setScreen(lastScreen);
            ScreenCache.get().setLastScreen(bookId, lang, lastScreen);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(super.mouseClicked(mouseX, mouseY, button))
            return true;
        if(button == 1) {
            tryBackButton();
            return true;
        }
        return false;
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
    public Style getHeaderStyle() {
        return Style.EMPTY.withColor(book.getHeaderColour());
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
     * @return True if this screen is the "top level" of a book, e.g a landing screen
     */
    protected boolean isTopLevel() {
        return false;
    }

}
