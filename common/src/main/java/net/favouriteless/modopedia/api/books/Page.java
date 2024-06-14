package net.favouriteless.modopedia.api.books;

import java.util.List;

/**
 * Interface representing a single page or template within an {@link Entry}.
 */
public interface Page {

    List<PageComponent> getComponents();

}
