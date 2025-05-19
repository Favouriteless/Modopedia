package net.favouriteless.modopedia.api.books.page_components;

import net.favouriteless.modopedia.api.Lookup;
import net.favouriteless.modopedia.api.books.Book;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.level.Level;

/**
 * Base class for component implementations; an instance of the component will be created for every time it is present
 * a page so storing data here is fine.
 */
public abstract class PageComponent implements PageRenderable, PageEventListener {

    protected int x;
    protected int y;
    protected int pageNum;
    protected String entryId;

    /**
     * Called when the component is first created. Use init to grab any values from the lookup. Lookup will only be
     * provided once.
     */
    public void init(Book book, Lookup lookup, Level level) {
        this.x = lookup.getOrDefault("x", 0).asInt();
        this.y = lookup.getOrDefault("y", 0).asInt();
        this.pageNum = lookup.get("page_num").asInt();
        this.entryId = lookup.get("entry").asString();
    }

    /**
     * Called every frame. The pose is transformed so that (0, 0) is the top left corner of the page.
     */
    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick) {}

    /**
     * Called when this component first gets added to a Screen. Components must use {@link PageRenderable} and
     * {@link PageEventListener} widgets so that widgets can access the render context.
     */
    public void initWidgets(PageWidgetHolder holder, BookRenderContext context) {}

}
