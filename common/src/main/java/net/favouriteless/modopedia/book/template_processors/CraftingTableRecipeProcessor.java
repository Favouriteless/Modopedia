package net.favouriteless.modopedia.book.template_processors;

import net.favouriteless.modopedia.api.Lookup.MutableLookup;
import net.favouriteless.modopedia.api.Variable;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookTexture;
import net.favouriteless.modopedia.api.books.BookTexture.Rectangle;
import net.favouriteless.modopedia.api.books.TemplateProcessor;
import net.favouriteless.modopedia.api.registries.BookTextureRegistry;
import net.minecraft.world.level.Level;

public abstract class CraftingTableRecipeProcessor implements TemplateProcessor {

    protected static final int GRID_WIDTH = 50; // 16 * 3 + 2

    @Override
    public void init(Book book, MutableLookup lookup, Level level) {
        BookTexture tex = BookTextureRegistry.get().getTexture(book.getTexture());
        if(tex == null)
            throw new IllegalStateException("Crafting grid templates require the book to have a valid BookTexture");

        Rectangle grid = tex.widgets().get("crafting_grid");
        Rectangle arrow = tex.widgets().get("crafting_arrow");
        Rectangle frame = tex.widgets().get("small_frame");

        if(grid == null || arrow == null || frame == null)
            throw new IllegalStateException("Crafting grid templates require the BookTexture to have crafting_grid, crafting_arrow and small_frame widgets");

        int arrowX = GRID_WIDTH + ((grid.width() - GRID_WIDTH) / 2) + 2;

        lookup.set("arrow_x", Variable.of(arrowX));
        lookup.set("arrow_y", Variable.of(GRID_WIDTH / 2 - arrow.height() / 2)); // Grid offset ignored as (0,0) is the first item, not the frame
        lookup.set("arrow_width", Variable.of(arrow.width()));
        lookup.set("arrow_height", Variable.of(arrow.height()));

        lookup.set("output_x", Variable.of(arrowX + arrow.width() + 2 + (frame.width() - 16) / 2));
    }
}
