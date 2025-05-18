package net.favouriteless.modopedia.api.books;

import net.favouriteless.modopedia.api.books.page_components.PageComponent;

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
