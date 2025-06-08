package net.favouriteless.modopedia.client.screens.books.book_screen_pages;

import com.mojang.blaze3d.vertex.PoseStack;
import net.favouriteless.modopedia.api.books.BookTexture;
import net.favouriteless.modopedia.api.books.BookTexture.Rectangle;
import net.favouriteless.modopedia.client.screens.books.BookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;

public class TitledScreenPage extends ScreenPage {

    protected final Component title;
    protected final int titleX;
    protected final int titleY;

    public TitledScreenPage(BookScreen parent, Component title, int titleX, int titleY) {
        super(parent);
        this.title = title;
        this.titleX = titleX;
        this.titleY = titleY;
    }

    @Override
    public void render(GuiGraphics graphics, PoseStack poseStack, Rectangle dimensions, int mouseX, int mouseY, float partialTick) {
        TextColor color = title.getStyle().getColor();
        graphics.drawString(Minecraft.getInstance().font, title, titleX, titleY, color != null ? color.getValue() : 0, false);

        BookTexture texture = parent.getBookTexture();
        Rectangle separator = texture.separator();
        int x = parent.getBook().getLineWidth() / 2 - separator.width()/2;

        graphics.blit(texture.location(), x, titleY + 10, separator.u(), separator.v(), separator.width(), separator.height(), texture.texWidth(), texture.texHeight());
    }

}
