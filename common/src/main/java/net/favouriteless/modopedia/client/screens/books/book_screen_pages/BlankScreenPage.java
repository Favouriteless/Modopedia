package net.favouriteless.modopedia.client.screens.books.book_screen_pages;

import com.mojang.blaze3d.vertex.PoseStack;
import net.favouriteless.modopedia.api.book.BookTexture.Rectangle;
import net.favouriteless.modopedia.client.screens.books.BookScreen;
import net.minecraft.client.gui.GuiGraphics;

public class BlankScreenPage extends ScreenPage {

    public BlankScreenPage(BookScreen<?> parent) {
        super(parent);
    }

    @Override
    public void render(GuiGraphics graphics, PoseStack poseStack, Rectangle dimensions, int mouseX, int mouseY, float partialTick) {
    }

}
