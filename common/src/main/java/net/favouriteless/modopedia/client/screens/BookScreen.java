package net.favouriteless.modopedia.client.screens;

import net.favouriteless.modopedia.api.BookRegistry;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookRenderContext;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

public abstract class BookScreen extends Screen implements BookRenderContext {

    public static final int FULL_WIDTH = 271;
    public static final int FULL_HEIGHT = 180;

    protected final ResourceLocation bookId;
    protected final Book book;
    protected final ResourceLocation texture;

    protected int ticks = 0;

    public BookScreen(Book book) {
        super(Component.literal(book.getTitle()));
        this.bookId = BookRegistry.get().getId(book);
        this.book = book;
        this.texture = book.getTexture();
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.renderTransparentBackground(graphics);
        graphics.blit(texture, (width - FULL_WIDTH) / 2, (height - FULL_HEIGHT) / 2, 0, 0, FULL_WIDTH, FULL_HEIGHT, 512, 512);
    }

    @Override
    public void tick() {
        ticks++;
    }

    @Override
    public Screen getScreen() {
        return this;
    }

    @Override
    public Style getStyle() {
        return null;
    }

    @Override
    public void renderIngredient(GuiGraphics graphics, int x, int y, int mouseX, int mouseY, Ingredient ingredient) {

    }

    @Override
    public void renderIngredientNoTooltip(GuiGraphics graphics, int x, int y, int mouseX, int mouseY, Ingredient ingredient) {

    }

    @Override
    public boolean isHovered(int mouseX, int mouseY, int x, int y, int width, int height) {
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
