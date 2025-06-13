package net.favouriteless.modopedia.client.screens.books.book_screen_pages;

import com.mojang.blaze3d.vertex.PoseStack;
import net.favouriteless.modopedia.api.books.BookTexture.Rectangle;
import net.favouriteless.modopedia.book.text.TextChunk;
import net.favouriteless.modopedia.client.screens.books.BookScreen;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;

public class FormattedTextPage extends ScreenPage {

    protected final int textX;
    protected final int textY;
    protected final List<TextChunk> landingText;

    public FormattedTextPage(BookScreen parent, List<TextChunk> landingText, int textX, int textY) {
        super(parent);
        this.textX = textX;
        this.textY = textY;
        this.landingText = landingText;
    }

    @Override
    public void render(GuiGraphics graphics, PoseStack poseStack, Rectangle dimensions, int mouseX, int mouseY, float partialTick) {
        for(TextChunk chunk : landingText) {
            chunk.render(graphics, textX, textY, mouseX - textX, mouseY - textY);
        }
    }



}
