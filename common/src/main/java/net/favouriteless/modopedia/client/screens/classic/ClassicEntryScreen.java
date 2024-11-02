package net.favouriteless.modopedia.client.screens.classic;

import com.mojang.blaze3d.vertex.PoseStack;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.client.screens.BookScreen;
import net.minecraft.client.gui.GuiGraphics;

public class ClassicEntryScreen extends BookScreen {

    public static final int PAGE_1_X = 21;
    public static final int PAGE_2_X = 143;

    public static final int PAGE_Y = 19;

    protected final Entry entry;

    public ClassicEntryScreen(Book book, Entry entry, BookScreen lastScreen) {
        super(book, lastScreen);
        this.entry = entry;
    }

    public ClassicEntryScreen(Book book, Entry entry) {
        this(book, entry, null);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);

        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();

        int xShift = leftPos + PAGE_1_X;
        int yShift = topPos + PAGE_Y;

        poseStack.translate(xShift, yShift, 0);

        mouseX -= xShift; // Offset mouse pos by page position for hovers-- components work in local space.
        mouseY -= yShift;

        for(PageComponent component : entry.getPages().get(0).getComponents()) {
            component.render(graphics, this, mouseX, mouseY, partialTick);
        }

        poseStack.popPose();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        double x = mouseX - (leftPos + PAGE_1_X);
        double y = mouseY - (topPos + PAGE_Y);
        for(PageComponent component : entry.getPages().get(0).getComponents()) {
            if(component.pageClicked(this, x, y, button))
                return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

}
