package net.favouriteless.modopedia.client.screens.classic;

import com.mojang.blaze3d.vertex.PoseStack;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookTexture.PageDetails;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.api.books.Page;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.api.books.page_components.PageEventListener;
import net.favouriteless.modopedia.api.books.page_components.PageRenderable;
import net.favouriteless.modopedia.api.books.page_components.PageWidgetHolder;
import net.favouriteless.modopedia.client.screens.BookScreen;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.List;

public class EntryScreen extends BookScreen implements PageWidgetHolder {

    // All of our page widgets/renderables are separate to regular ones so we can hide/reveal them as needed.
    protected final List<List<PageRenderable>> pageRenderables = new ArrayList<>();
    protected final List<List<PageEventListener>> pageWidgets = new ArrayList<>();
    protected boolean pageDragging = false;
    protected PageEventListener pageFocused = null;

    protected final Entry entry;

    protected int leftPage = 0; // Index of the leftmost page being displayed.

    public EntryScreen(Book book, Entry entry, BookScreen lastScreen) {
        super(book, lastScreen);
        this.entry = entry;

        for(Page page : entry.getPages()) {
            List<PageRenderable> renderables = addToList(pageRenderables, new ArrayList<>());
            List<PageEventListener> listeners = addToList(pageWidgets, new ArrayList<>());

            for(PageComponent component : page.getComponents())
                component.initWidgets(this);

            renderables.addAll(page.getComponents());
            listeners.addAll(page.getComponents());
        }
    }

    public EntryScreen(Book book, Entry entry) {
        this(book, entry, null);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);

        for(int i = 0; i < texture.pages().size(); i++) {
            tryRenderPage(graphics, i, mouseX, mouseY, partialTick);
        }
    }

    private void tryRenderPage(GuiGraphics graphics, int index, int mouseX, int mouseY, float partialTick) {
        if(leftPage+index >= pageRenderables.size())
            return;

        PageDetails details = texture.pages().get(index);
        PoseStack poseStack = graphics.pose();
        int xShift = leftPos + details.x();
        int yShift = topPos + details.y();

        poseStack.pushPose();
        poseStack.translate(xShift, yShift, 0);

        mouseX -= xShift; // Offset mouse pos by page position-- components work in local space.
        mouseY -= yShift;

        for(PageRenderable renderable : pageRenderables.get(leftPage+index))
            renderable.render(graphics, this, mouseX, mouseY, partialTick);

        poseStack.popPose();
    }

    /**
     * x and y are assumed to be local compared to leftPos and topPos.
     */
    protected int getPageIndex(double x, double y) {
        for(int i = 0; i < texture.pages().size(); i++) {
            PageDetails page = texture.pages().get(i);
            if(isHovered(x, y, page.x(), page.y(), page.width(), page.height()))
                return i;
        }
        return -1;
    }

    private boolean tryStartDragging(PageEventListener listener, int button) {
        pageFocused = listener;
        if(button == 0)
            pageDragging = true;
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int pageIndex = getPageIndex(mouseX-leftPos, mouseY-topPos); // We only care about the page the mouse is on for clicking
        if(pageIndex != -1) {
            int index = leftPage + pageIndex;
            if(index < pageWidgets.size()) {
                PageDetails details = texture.pages().get(pageIndex);
                for(PageEventListener widget : pageWidgets.get(index)) {
                    if(widget.mouseClicked(this, mouseX - (leftPos + details.x()), mouseY - (topPos + details.y()), button))
                        return tryStartDragging(widget, button);
                }
            }
        }
        pageFocused = null;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if(button == 0 && pageDragging) { // On release, first we check if there was a focused widget.
            pageDragging = false;
            if(pageFocused != null)
                return pageFocused.mouseReleased(this, mouseX, mouseY, button);
        }

        int pageIndex = getPageIndex(mouseX-leftPos, mouseY-topPos); //... then check the page the mouse is on if it wasn't consumed.
        if(pageIndex != -1) {
            int index = leftPage + pageIndex;
            if(index < pageWidgets.size()) {
                PageDetails details = texture.pages().get(pageIndex);
                for(PageEventListener widget : pageWidgets.get(index)) {
                    if(widget.mouseReleased(this, mouseX - (leftPos + details.x()), mouseY - (topPos + details.y()), button))
                        return true;
                }
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if(pageFocused != null && pageDragging && button == 0 && // We ONLY care about dragging the focused element
                pageFocused.mouseDragged(this, mouseX, mouseY, button, dragX, dragY))
            return true;

        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int pageIndex = getPageIndex(mouseX-leftPos, mouseY-topPos); // Only care about the page the scroll was on
        if(pageIndex != -1) {
            int index = leftPage + pageIndex;
            if(index < pageWidgets.size()) {
                PageDetails details = texture.pages().get(pageIndex);
                for(PageEventListener widget : pageWidgets.get(index)) {
                    if(widget.mouseScrolled(this, mouseX - (leftPos + details.x()), mouseY - (topPos + details.y()), scrollX, scrollY))
                        return true;
                }
            }
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(pageFocused != null && pageFocused.keyPressed(this, keyCode, scanCode, modifiers))
            return true;
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if(pageFocused != null && pageFocused.keyReleased(this, keyCode, scanCode, modifiers))
            return true;
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if(pageFocused != null && pageFocused.charTyped(this, codePoint, modifiers))
            return true;
        return super.charTyped(codePoint, modifiers);
    }

    @Override
    public <T extends PageRenderable> T addRenderable(T renderable, int pageNum) {
        pageRenderables.get(pageNum).add(renderable);
        return renderable;
    }

    @Override
    public <T extends PageEventListener> T addWidget(T widget, int pageNum) {
        pageWidgets.get(pageNum).add(widget);
        return widget;
    }

    @Override
    public <T extends PageRenderable & PageEventListener> T addRenderableWidget(T widget, int pageNum) {
        pageRenderables.get(pageNum).add(widget);
        pageWidgets.get(pageNum).add(widget);
        return widget;
    }

    public static <T> T addToList(List<T> list, T value) {
        list.add(value);
        return value;
    }

}
