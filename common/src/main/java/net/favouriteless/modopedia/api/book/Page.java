package net.favouriteless.modopedia.api.book;

import net.favouriteless.modopedia.api.book.page_components.PageComponent;

import java.util.Collection;

/**
 * An individual page full of {@link PageComponent}s within an {@link Entry}.
 */
public interface Page {

    /**
     * @return A {@link Collection} containing all {@link PageComponent}s on this page.
     */
    Collection<PageComponent> getComponents();

}
