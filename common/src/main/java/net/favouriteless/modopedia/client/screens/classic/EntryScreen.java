package net.favouriteless.modopedia.client.screens.classic;

import com.mojang.blaze3d.vertex.PoseStack;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.api.books.Page;
import net.favouriteless.modopedia.api.books.PageDetails;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.client.screens.BookScreen;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;

public class EntryScreen extends BookScreen {

    protected final Entry entry;
    protected final List<Page> pages;
    protected final PageDetails[] pageDetails; // This lets us re-use EntryScreen for screens with any number, pos and size of page

    protected int leftPage = 0; // Index of the leftmost page being displayed.

    public EntryScreen(Book book, Entry entry, BookScreen lastScreen, PageDetails... pageDetails) {
        super(book, lastScreen);
        this.texWidth = 271;
        this.texHeight = 180;

        this.entry = entry;
        this.pages = entry.getPages();
        this.pageDetails = pageDetails;
    }

    public EntryScreen(Book book, Entry entry, PageDetails... pageDetails) {
        this(book, entry, null, pageDetails);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);

        for(int i = 0; i < pageDetails.length; i++) {
            tryRenderPage(graphics, i, mouseX, mouseY, partialTick);
        }
    }

    private void tryRenderPage(GuiGraphics graphics, int index, int mouseX, int mouseY, float partialTick) {
        Page page = getPage(index);
        if(page == null)
            return;

        PageDetails pageDetails = this.pageDetails[index];
        PoseStack poseStack = graphics.pose();
        int xShift = leftPos + pageDetails.x();
        int yShift = topPos + pageDetails.y();

        poseStack.pushPose();
        poseStack.translate(xShift, yShift, 0);

        mouseX -= xShift; // Offset mouse pos by page position-- components work in local space.
        mouseY -= yShift;

        for(PageComponent component : page.getComponents()) {
            component.render(graphics, this, mouseX, mouseY, partialTick);
        }

        poseStack.popPose();
    }

    protected Page getPage(int index) {
        return pages.size() > index ? pages.get(index) : null;
    }

    /**
     * x and y are assumed to be in local space.
     */
    protected int getPageIndex(double x, double y) {
        for(int i = 0; i < pageDetails.length; i++) {
            PageDetails pageDetails = this.pageDetails[i];
            if(isHovered(x, y, pageDetails.x(), pageDetails.y(), pageDetails.width(), pageDetails.height()))
                return i;
        }
        return -1;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int pageIndex = getPageIndex(mouseX-leftPos, mouseY-topPos);
        if(pageIndex != -1) {
            Page page = getPage(leftPage+pageIndex);
            if(page != null) {
                PageDetails pageDetails = this.pageDetails[pageIndex];
                for(PageComponent component : page.getComponents()) {
                    if(component.mouseClicked(this, mouseX - (leftPos + pageDetails.x()), mouseY - (topPos + pageDetails.y()), button))
                        return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for(int i = 0; i < pageDetails.length; i++) {
            Page page = getPage(leftPage + i);
            if(page == null)
                break;

            PageDetails pageDetails = this.pageDetails[i];
            for(PageComponent component : page.getComponents()) {
                if(component.mouseReleased(this, mouseX - (leftPos + pageDetails.x()), mouseY - (topPos + pageDetails.y()), button))
                    return true;
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        for(int i = 0; i < pageDetails.length; i++) {
            Page page = getPage(leftPage + i);
            if(page == null)
                break;

            PageDetails pageDetails = this.pageDetails[i];
            for(PageComponent component : page.getComponents()) {
                if(component.mouseDragged(this, mouseX - (leftPos + pageDetails.x()), mouseY - (topPos + pageDetails.y()), button, dragX, dragY))
                    return true;
            }
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int pageIndex = getPageIndex(mouseX-leftPos, mouseY-topPos);
        if(pageIndex != -1) {
            Page page = getPage(leftPage+pageIndex);
            if(page != null) {
                PageDetails pageDetails = this.pageDetails[pageIndex];
                for(PageComponent component : page.getComponents()) {
                    if(component.mouseScrolled(this, mouseX - (leftPos + pageDetails.x()), mouseY - (topPos + pageDetails.y()), scrollX, scrollY))
                        return true;
                }
            }
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        for(int i = 0; i < pageDetails.length; i++) {
            Page page = getPage(leftPage + i);
            if(page == null)
                break;
            for(PageComponent component : page.getComponents()) {
                if(component.keyPressed(this, keyCode, scanCode, modifiers))
                    return true;
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        for(int i = 0; i < pageDetails.length; i++) {
            Page page = getPage(leftPage + i);
            if(page == null)
                break;
            for(PageComponent component : page.getComponents()) {
                if(component.keyReleased(this, keyCode, scanCode, modifiers))
                    return true;
            }
        }
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        for(int i = 0; i < pageDetails.length; i++) {
            Page page = getPage(leftPage + i);
            if(page == null)
                break;
            for(PageComponent component : page.getComponents()) {
                if(component.charTyped(this, codePoint, modifiers))
                    return true;
            }
        }
        return super.charTyped(codePoint, modifiers);
    }

}
