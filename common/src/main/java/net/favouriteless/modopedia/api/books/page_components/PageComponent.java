package net.favouriteless.modopedia.api.books.page_components;

import net.favouriteless.modopedia.api.Variable;
import net.favouriteless.modopedia.api.books.BookRenderContext;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.level.Level;

/**
 * <p>
 *     Base class for component implementations; an instance of the component will be created for every time it
 *     is present on a page-- storing data here is fine.
 * </p>
 */
public abstract class PageComponent {

    protected int x;
    protected int y;
    protected int pageNum;

    /**
     * Called when the component is first created. Data-driven content will not be accessible yet (e.g. recipes).
     */
    public void init(Variable.Lookup lookup) {
        this.x = lookup.get("x").asInt();
        this.y = lookup.get("y").asInt();
        this.pageNum = lookup.get("pageNum").asInt();
    }

    /**
     * Called when datapacks are reloaded or synchronised; use this for any data-driven setup (e.g. fetching a recipe)
     */
    public void refreshData(Level level, Variable.Lookup lookup) {}

    /**
     * Called every render frame. The pose is transformed so that (0, 0) is the top left corner of the page.
     * You are responsible for all other transformations.
     */
    public abstract void render(GuiGraphics graphics, BookRenderContext context, int xMouse, int yMouse,
                                float partialTicks);

}