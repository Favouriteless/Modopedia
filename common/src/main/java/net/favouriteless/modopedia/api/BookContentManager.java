package net.favouriteless.modopedia.api;

import net.favouriteless.modopedia.api.books.BookContent;
import net.favouriteless.modopedia.client.BookContentManagerImpl;
import net.minecraft.resources.ResourceLocation;

/**
 * BookContentManager maps all existing {@link BookContent}s to their book ID. Will only be populated client side.
 */
public interface BookContentManager {

    static BookContentManager get() {
        return BookContentManagerImpl.INSTANCE;
    }

    /**
     * @return The content for the given book ID.
     */
    BookContent getContent(ResourceLocation id);

    /**
     * @return The book ID the given content belongs to.
     */
    ResourceLocation getBookId(BookContent content);

    /**
     * Register (or overwrite) the content of a book.
     */
    void register(ResourceLocation id, BookContent content);

    /**
     * Remove all BookContents from the manager.
     */
    void clear();

    /**
     * Remove the contents of a specific book from the manager.
     *
     * @param id The ID of the book to be removed.
     */
    void remove(ResourceLocation id);

}
