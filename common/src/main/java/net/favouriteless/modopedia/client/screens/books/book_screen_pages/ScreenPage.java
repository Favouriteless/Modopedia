package net.favouriteless.modopedia.client.screens.books.book_screen_pages;

import com.mojang.blaze3d.vertex.PoseStack;
import net.favouriteless.modopedia.api.books.BookTexture.Rectangle;
import net.favouriteless.modopedia.api.books.Page;
import net.favouriteless.modopedia.api.books.page_components.BookRenderContext;
import net.favouriteless.modopedia.client.screens.books.BookScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a developer-made page within a book GUI, not to be confused with {@link Page}.
 */
public abstract class ScreenPage {

    protected final BookScreen parent;
    protected final Set<AbstractWidget> widgets;

    public ScreenPage(BookScreen parent) {
        this.parent = parent;
        this.widgets = new HashSet<>();
    }

    /**
     *
     * @param graphics {@link GuiGraphics} belonging to the parent {@link Screen}
     * @param poseStack {@link PoseStack} belonging to graphics
     * @param dimensions {@link Rectangle} object for where this page is placed on it's texture
     * @param mouseX X position of the mouse relative to this page
     * @param mouseY Y position of the mouse relative to this page
     * @param partialTick
     */
    public abstract void render(GuiGraphics graphics, PoseStack poseStack, Rectangle dimensions, int mouseX, int mouseY, float partialTick);

    public void hideWidgets() {
        for(AbstractWidget widget : widgets) {
            widget.visible = false;
            widget.active = false;
        }
    }

    public void showWidgets() {
        for(AbstractWidget widget : widgets) {
            widget.visible = true;
            widget.active = true;
        }
    }

    public void addWidget(AbstractWidget widget) {
        widgets.add(widget);
    }

    public Set<AbstractWidget> getWidgets() {
        return widgets;
    }

    public void tick(BookRenderContext context) {}

}
