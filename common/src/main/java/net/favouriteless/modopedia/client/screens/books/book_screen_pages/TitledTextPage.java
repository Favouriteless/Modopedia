package net.favouriteless.modopedia.client.screens.books.book_screen_pages;

import com.mojang.blaze3d.vertex.PoseStack;
import net.favouriteless.modopedia.api.book.BookTexture;
import net.favouriteless.modopedia.api.book.BookTexture.Rectangle;
import net.favouriteless.modopedia.book.text.TextChunk;
import net.favouriteless.modopedia.client.screens.books.BookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;

import java.util.List;

public class TitledTextPage extends FormattedTextPage {

    protected final Component title;
    protected final Rectangle page;

    public TitledTextPage(BookScreen<?> parent, Component title, List<TextChunk> text, int textY, Rectangle page) {
        super(parent, text, 0, textY);
        this.title = title;
        this.page = page;
    }

    @Override
    public void render(GuiGraphics graphics, PoseStack poseStack, Rectangle dimensions, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, poseStack, dimensions, mouseX, mouseY, partialTick);
        Font font = Minecraft.getInstance().font;
        TextColor color = title.getStyle().getColor();

        int x = page.width() / 2 - font.width(title) / 2;
        graphics.drawString(font, title, x, 0, color != null ? color.getValue() : 0, false);

        BookTexture texture = parent.getBookTexture();
        Rectangle separator = texture.widgets().get("separator");
        if(separator == null)
            return;

        x = page.width() / 2 - separator.width() / 2;
        graphics.blit(texture.location(), x, 10, separator.u(), separator.v(), separator.width(), separator.height(), texture.texWidth(), texture.texHeight());
    }

}
