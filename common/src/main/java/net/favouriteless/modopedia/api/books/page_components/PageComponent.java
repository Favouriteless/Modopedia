package net.favouriteless.modopedia.api.books.page_components;

import net.favouriteless.modopedia.api.Variable;
import net.favouriteless.modopedia.api.books.Book;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Base class for component implementations; an instance of the component will be created for every time it
 *     is present on a page so storing data here is fine.
 * </p>
 */
public abstract class PageComponent implements PageComponentEventHandler, PageRenderable {

    private final List<PageRenderable> renderables = new ArrayList<>();
    private final List<PageEventListener> widgets = new ArrayList<>();
    private boolean isDragging = false;
    private PageEventListener focused = null;

    protected int x;
    protected int y;
    protected int pageNum;

    /**
     * Called when the component is first created. Use this to grab any values from the lookup.
     */
    public void init(Book book, Variable.Lookup lookup, Level level) {
        this.x = lookup.getOrDefault("x", 0).asInt();
        this.y = lookup.getOrDefault("y", 0).asInt();
        this.pageNum = lookup.get("page_num").asInt();
    }

    /**
     * Called every render frame. The pose is transformed so that (0, 0) is the top left corner of the page.
     * You are responsible for all other transformations.
     */
    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick) {
        for (PageRenderable renderable : this.renderables) {
            renderable.render(graphics, context, mouseX, mouseY, partialTick);
        }
    }

    protected <T extends PageRenderable> T addRenderable(T widget) {
        renderables.add(widget);
        return widget;
    }

    protected <T extends PageRenderable & PageEventListener> T addRenderableWidget(T widget) {
        renderables.add(widget);
        widgets.add(widget);
        return widget;
    }

    // You can ignore stuff below this point, it isn't needed for normal PageComponent implementation.

    @Override
    public List<? extends PageEventListener> children() {
        return widgets;
    }

    @Override
    public boolean isDragging() {
        return isDragging;
    }

    @Override
    public void setDragging(boolean isDragging) {
        this.isDragging = isDragging;
    }

    @Override
    public @Nullable PageEventListener getFocused() {
        return focused;
    }

    @Override
    public void setFocused(@Nullable PageEventListener focused) {
        this.focused = focused;
    }

}
