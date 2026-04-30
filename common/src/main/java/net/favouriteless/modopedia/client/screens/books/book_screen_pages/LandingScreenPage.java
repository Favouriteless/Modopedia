package net.favouriteless.modopedia.client.screens.books.book_screen_pages;

import com.mojang.blaze3d.vertex.PoseStack;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.BookTexture;
import net.favouriteless.modopedia.api.book.BookTexture.FixedRectangle;
import net.favouriteless.modopedia.api.book.BookTexture.Rectangle;
import net.favouriteless.modopedia.book.text.Justify;
import net.favouriteless.modopedia.book.text.TextChunk;
import net.favouriteless.modopedia.book.text.TextParser;
import net.favouriteless.modopedia.client.screens.books.BookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;

import java.util.List;

public class LandingScreenPage extends FormattedTextPage {

    protected final Component title;
    protected final Component subtitle;

    private final int titleX;
    private final int titleY;
    private final int subtitleX;

    public LandingScreenPage(BookScreen<?> parent, Component title, Component subtitle, int titleX, int titleY, int subtitleX,
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

        if(subtitle != null) {
            int subtitleX = backerX + backer.width() - (font.width(subtitle) + this.subtitleX);
            int subtitleY = titleY + font.lineHeight - 2;

            graphics.drawString(font, subtitle, subtitleX, subtitleY, color != null ? color.getValue() : 0, false);
        }

        poseStack.pushPose();
        final var yShift = (backer.y() + backer.height()) - rectangle.v();
        poseStack.translate(0, yShift, 0);

        super.render(graphics, poseStack, rectangle, mouseX, mouseY - yShift, partialTick);

        poseStack.popPose();

    }

    @Override
    public boolean mouseClicked(Rectangle rectangle, double mouseX, double mouseY, int button) {
        BookTexture texture = parent.getBookTexture();
        FixedRectangle backer = texture.titleBacker();
        final var yShift = (backer.y() + backer.height()) - rectangle.v();
        return super.mouseClicked(rectangle, mouseX, mouseY - yShift, button);
    }

    /**
     * @return A {@link LandingScreenPage}, or a simple error page if the landing text cannot be parsed.
     */
    public static ScreenPage create(BookScreen<?> parent, Component subtitle, String language) {
        String rawLandingText = parent.getBook().getRawLandingText();
        if(rawLandingText != null)
            rawLandingText = Language.getInstance().getOrDefault(rawLandingText);

        int lineWidth = parent.getBookTexture().pages().getFirst().width();
        int lineHeight = Minecraft.getInstance().font.lineHeight;

        List<TextChunk> landingText;

        try {
            landingText = TextParser.parse(rawLandingText, parent.getStyle(), lineWidth, lineHeight, language, Justify.LEFT);
        }
        catch(Exception e) {
            String key = Modopedia.translation("screen", "error");
            String text = String.format("$(f:minecraft:default)$(c:#FF0000)$(t:%s)%s", e.getMessage(), Language.getInstance().getOrDefault(key));

            // If it errors again well then fuck it, the mod earned the crash.
            landingText = TextParser.parse(text, parent.getStyle(), lineWidth, lineHeight, language, Justify.LEFT);
        }

        return new LandingScreenPage(parent, parent.getTitle(), subtitle, 37, 7, 10, landingText, 0, 0);
    }

}
