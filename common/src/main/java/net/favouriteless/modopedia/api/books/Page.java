package net.favouriteless.modopedia.api.books;

import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.minecraft.world.level.Level;

import java.util.Collection;

/**
 * Interface representing a single page or template within an {@link Entry}.
 */
public interface Page {

    /**
     * @return A {@link Collection} containing all {@link PageComponent}s on this page.
     */
    Collection<PageComponent> getComponents();

    /**
     * Called every time data is reloaded or synchronised.
     */
    void onDataReload(Level level);

}
