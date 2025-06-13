package net.favouriteless.modopedia.api.book.page_components;

import net.minecraft.client.gui.components.AbstractWidget;

/**
 * An alternative to {@link AbstractWidget} used for widgets instantiated by
 * {@link PageComponent#initWidgets(PageWidgetHolder, BookRenderContext)}. Differs to a normal widget in that the
 * {@link BookRenderContext} is passed into render and input handling methods.
 */
public abstract class AbstractPageWidget implements PageEventListener, PageRenderable {

    public final int x;
    public final int y;
    public final int width;
    public final int height;

    public boolean active = true;

    protected AbstractPageWidget(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean shouldRender() {
        return active;
    }

}
