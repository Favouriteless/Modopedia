package net.favouriteless.modopedia.api.books.page_components;

import net.favouriteless.modopedia.api.Variable;
import net.favouriteless.modopedia.api.books.Book;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.level.Level;

/**
 * <p>
 *     Base class for component implementations; an instance of the component will be created for every time it
 *     is present on a page so storing data here is fine.
 * </p>
 */
public abstract class PageComponent {

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
    public abstract void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY,
                                float partialTicks);

    /**
     * Called when mouse is clicked on the page this component is on.
     *
     * @return True if the click should be consumed by this component.
     */
    public boolean pageClicked(BookRenderContext context, double mouseX, double mouseY, int button) {
        return false;
    }

}
