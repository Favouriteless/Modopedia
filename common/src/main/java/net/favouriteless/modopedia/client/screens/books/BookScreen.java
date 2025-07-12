package net.favouriteless.modopedia.client.screens.books;

import com.mojang.blaze3d.platform.InputConstants;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.ScreenCache;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.book.BookTexture;
import net.favouriteless.modopedia.api.book.BookTexture.FixedRectangle;
import net.favouriteless.modopedia.api.book.BookType;
import net.favouriteless.modopedia.api.book.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.registries.client.BookTextureRegistry;
import net.favouriteless.modopedia.api.registries.common.BookRegistry;
import net.favouriteless.modopedia.book.StudyManager;
import net.favouriteless.modopedia.book.loading.BookContentLoader;
import net.favouriteless.modopedia.book.registries.client.ItemAssociationRegistry.EntryAssociation;
import net.favouriteless.modopedia.client.BookOpenHandler;
import net.favouriteless.modopedia.client.init.MKeyMappings;
import net.favouriteless.modopedia.client.screens.widgets.BookImageButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;

public abstract class BookScreen<T extends BookType> extends Screen implements BookRenderContext {

    protected final ResourceLocation bookId;
    protected final Book book;
    protected final T type;
    protected final String language;
    protected final LocalisedBookContent content;
    protected final BookTexture texture;
    protected final BookScreen<?> lastScreen;

    protected int ticks = 0;
    protected int leftPos = 0;
    protected int topPos = 0;

    public BookScreen(Book book, T type, String language, LocalisedBookContent content, BookScreen<?> lastScreen, Component title) {
        super(title);
        this.bookId = BookRegistry.get().getId(book);
        this.book = book;
        this.type = type;
        this.language = language;
        this.content = content;
        this.texture = BookTextureRegistry.get().getTexture(book.getTexture());
        this.lastScreen = lastScreen;
        ScreenCache.get().setLastScreen(bookId, this.language, this);
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
            ScreenCache.get().setLastScreen(bookId, language, null);
            BookOpenHandler.tryOpenBook(bookId);
        }
        else {
            minecraft.setScreen(lastScreen);
            ScreenCache.get().setLastScreen(bookId, language, lastScreen);
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
    public BookScreen<T> getScreen() {
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

    @Override
    public void renderItem(GuiGraphics graphics, ItemStack item, int x, int y, int mouseX, int mouseY, String entry) {
        graphics.renderItem(item, x, y);
        graphics.renderItemDecorations(font, item, x, y);

        if(!isHovered(mouseX, mouseY, x, y, 16, 16))
            return;

        graphics.renderTooltip(font, item, mouseX, mouseY);

        EntryAssociation association = StudyManager.getAssociation(language, item.getItem());
        if(association == null || association.entryId().equals(entry))
            return;
        if(!InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), MKeyMappings.KEY_STUDY.key.getValue()))
            return;

        BookOpenHandler.tryOpenEntry(association.book(), association.entryId());
    }

    /**
     * @return True if this screen is the "top level" of a book, e.g a landing screen
     */
    protected boolean isTopLevel() {
        return false;
    }

}
