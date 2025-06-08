package net.favouriteless.modopedia.client.screens.books.book_screen_pages;

import com.mojang.blaze3d.vertex.PoseStack;
import net.favouriteless.modopedia.api.books.BookTexture;
import net.favouriteless.modopedia.api.books.BookTexture.Rectangle;
import net.favouriteless.modopedia.api.books.BookTexture.FixedRectangle;
import net.favouriteless.modopedia.book.text.TextChunk;
import net.favouriteless.modopedia.client.screens.books.BookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;

import java.util.List;

public class LandingScreenPage extends FormattedTextPage {

    protected final Component title;
    protected final Component subtitle;

    private final int titleX;
    private final int titleY;
    private final int subtitleX;

    public LandingScreenPage(BookScreen parent, Component title, Component subtitle, int titleX, int titleY, int subtitleX,
                             List<TextChunk> landingText, int textX, int textY) {
        super(parent, landingText, textX, textY);
        this.title = title;
        this.subtitle = subtitle;
        this.titleX = titleX;
        this.titleY = titleY;
        this.subtitleX = subtitleX;
    }

    @Override
    public void render(GuiGraphics graphics, PoseStack poseStack, Rectangle rectangle, int mouseX, int mouseY, float partialTick) {
        Font font = Minecraft.getInstance().font;
        BookTexture texture = parent.getBookTexture();
        FixedRectangle backer = texture.titleBacker();

        int backerX = backer.x() - rectangle.u();
        int backerY = backer.y() - rectangle.v();
        graphics.blit(texture.location(), backerX, backerY, backer.u(), backer.v(), backer.width(), backer.height(), texture.texWidth(), texture.texHeight());


        int titleX = backerX + this.titleX;
        int titleY = backerY + this.titleY;

        TextColor color = title.getStyle().getColor();
        graphics.drawString(font, title, titleX, titleY, color != null ? color.getValue() : 0, false);

        int subtitleX = backerX + backer.width() - (font.width(subtitle) + this.subtitleX);
        int subtitleY = titleY + font.lineHeight - 2;

        graphics.drawString(font, subtitle, subtitleX, subtitleY, color != null ? color.getValue() : 0, false);

        poseStack.pushPose();
        poseStack.translate(0, (backer.y() + backer.height()) - rectangle.v(), 0);

        super.render(graphics, poseStack, rectangle, mouseX, mouseY, partialTick);

        poseStack.popPose();

    }

}
