package net.favouriteless.modopedia.client.screens.classic;

import com.mojang.blaze3d.vertex.PoseStack;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.book.text.TextChunk;
import net.favouriteless.modopedia.book.text.TextParser;
import net.favouriteless.modopedia.client.screens.BookScreen;
import net.favouriteless.modopedia.util.StringUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.List;

public class ClassicLandingScreen extends BookScreen {

    protected static final int PAGE_1_X = 24;
    protected static final int PAGE_2_X = 147;
    protected static final int PAGE_Y = 15;
    protected static final int PAGE_WIDTH = 100;

    protected final Component title;
    protected final Component subtitle;

    protected final List<TextChunk> landingText;


    public ClassicLandingScreen(Book book, BookScreen lastScreen) {
        super(book, lastScreen);
        this.texWidth = 271;
        this.texHeight = 180;
        this.title = Component.literal(book.getTitle());
        this.subtitle = book.getSubtitle() != null ? Component.literal(book.getSubtitle()) : null;

        String rawLandingText = book.getRawLandingText();
        if(rawLandingText != null) {
            if(StringUtils.isTranslationKey(rawLandingText))
                rawLandingText = Language.getInstance().getOrDefault(rawLandingText);

            landingText = TextParser.parse(rawLandingText, book.getLineWidth(), 9, Style.EMPTY.withFont(book.getFont()).withColor(book.getTextColour()), 0, 0);
        }
        else {
            landingText = List.of();
        }
    }

    public ClassicLandingScreen(Book book) {
        this(book, null);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        PoseStack poseStack = graphics.pose();

        renderTitlePage(graphics, poseStack, mouseX, mouseY, partialTick);
    }

    protected void renderTitlePage(GuiGraphics graphics, PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        int xShift = leftPos + PAGE_1_X; // We shift our click positions and pose to be relative to the page
        int yShift = topPos + PAGE_Y;

        mouseX -= xShift;
        mouseY -= yShift;

        poseStack.pushPose();
        poseStack.translate(xShift, yShift, 0);

        poseStack.pushPose();
        poseStack.translate(0, 30, 0);
        Font font = Minecraft.getInstance().font;
        for(TextChunk chunk : landingText) {
            chunk.render(graphics, font, mouseX, mouseY);
        }
        poseStack.popPose();

        poseStack.popPose();
    }

    protected void renderCategoriesPage(GuiGraphics graphics, PoseStack poseStack, int mouseX, int mouseY, float partialTick) {

    }

}
