package net.favouriteless.modopedia.client.screens.books.book_screen_pages;

import com.mojang.blaze3d.vertex.PoseStack;
import net.favouriteless.modopedia.api.books.BookTexture.Rectangle;
import net.favouriteless.modopedia.api.books.Page;
import net.favouriteless.modopedia.api.books.page_components.*;
import net.favouriteless.modopedia.client.screens.books.BookScreen;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.List;

public class EntryScreenPage extends ScreenPage implements PageWidgetHolder, PageEventListener {

    protected final Rectangle rectangle;

    protected final List<PageRenderable> renderables = new ArrayList<>();
    protected final List<PageEventListener> widgets = new ArrayList<>();

    protected boolean dragging = false;
    protected PageEventListener focused = null;

    public EntryScreenPage(BookScreen parent, Rectangle rectangle, Page page) {
        super(parent);
        this.rectangle = rectangle;
        for(PageComponent component : page.getComponents()) {
            renderables.add(component);
            widgets.add(component);
            component.initWidgets(this, parent);
        }
    }

    @Override
    public void render(GuiGraphics graphics, PoseStack poseStack, Rectangle rectangle, int mouseX, int mouseY, float partialTick) {
        for(PageRenderable renderable : renderables) {
            if(renderable.shouldRender())
                renderable.render(graphics, parent, mouseX, mouseY, partialTick);
        }
    }

    @Override
    public void tick(BookRenderContext context) {
        for(PageRenderable renderable : renderables) {
            renderable.tick(context);
        }
    }

    @Override
    public <T extends PageRenderable> T addRenderable(T renderable) {
        renderables.add(renderable);
        return renderable;
    }

    @Override
    public <T extends PageEventListener> T addWidget(T widget) {
        widgets.add(widget);
        return widget;
    }

    @Override
    public <T extends PageRenderable & PageEventListener> T addRenderableWidget(T widget) {
        renderables.add(widget);
        widgets.add(widget);
        return widget;
    }

    @Override
    public boolean mouseClicked(BookRenderContext context, double mouseX, double mouseY, int button) {
        for(PageEventListener widget : widgets) {
            if(widget.mouseClicked(context, mouseX - rectangle.u(), mouseY - rectangle.v(), button)) {
                focused = widget;
                if(button == 0)
                    dragging = true;
                return true;
            }
        }
        focused = null;
        return false;
    }

    @Override
    public boolean mouseReleased(BookRenderContext context, double mouseX, double mouseY, int button) {
        if(button == 0 && dragging) { // On release, first we check if there was a focused widget.
            dragging = false;
            if(focused != null)
                return focused.mouseReleased(context, mouseX - rectangle.u(), mouseY - rectangle.v(), button);
        }

        for(PageEventListener widget : widgets) {
            if(widget.mouseReleased(context, mouseX - rectangle.u(), mouseY - rectangle.v(), button))
                return true;
        }

        return false;
    }

    @Override
    public boolean mouseDragged(BookRenderContext context, double mouseX, double mouseY, int button, double dragX, double dragY) {
        return focused != null && dragging && button == 0 && focused.mouseDragged(context, mouseX - rectangle.u(), mouseY - rectangle.v(), button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(BookRenderContext context, double mouseX, double mouseY, double scrollX, double scrollY) {
        for(PageEventListener widget : widgets) {
            if(widget.mouseScrolled(context, mouseX - rectangle.u(), mouseY - rectangle.v(), scrollX, scrollY))
                return true;
        }
        return false;
    }

    @Override
    public boolean keyPressed(BookRenderContext context, int keyCode, int scanCode, int modifiers) {
        return focused != null && focused.keyPressed(context, keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(BookRenderContext context, int keyCode, int scanCode, int modifiers) {
        return focused != null && focused.keyReleased(context, keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(BookRenderContext context, char codePoint, int modifiers) {
        return focused != null && focused.charTyped(context, codePoint, modifiers);
    }

}
