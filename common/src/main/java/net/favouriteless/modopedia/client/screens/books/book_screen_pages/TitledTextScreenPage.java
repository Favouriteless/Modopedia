package net.favouriteless.modopedia.client.screens.books.book_screen_pages;

import com.mojang.blaze3d.vertex.PoseStack;
import net.favouriteless.modopedia.api.books.BookTexture;
import net.favouriteless.modopedia.api.books.BookTexture.Rectangle;
import net.favouriteless.modopedia.book.text.TextChunk;
import net.favouriteless.modopedia.client.screens.books.BookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;

import java.util.List;

public class TitledTextScreenPage extends FormattedTextPage {

    protected final Component title;
    protected final int titleX;
    protected final int titleY;

    public TitledTextScreenPage(BookScreen parent, Component title, int titleX, int titleY,
                                List<TextChunk> landingText, int textX, int textY) {
        super(parent, landingText, textX, textY);
        this.title = title;
        this.titleX = titleX;
        this.titleY = titleY;
    }

    @Override
    public void render(GuiGraphics graphics, PoseStack poseStack, Rectangle dimensions, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, poseStack, dimensions, mouseX, mouseY, partialTick);
        TextColor color = title.getStyle().getColor();
        graphics.drawString(Minecraft.getInstance().font, title, titleX, titleY, color != null ? color.getValue() : 0, false);

        BookTexture texture = parent.getBookTexture();
        Rectangle separator = texture.separator();
        int x = parent.getBook().getLineWidth() / 2 - separator.width()/2;

        graphics.blit(texture.location(), x, titleY + 10, separator.x(), separator.y(), separator.width(), separator.height(), texture.texWidth(), texture.texHeight());
    }

}
