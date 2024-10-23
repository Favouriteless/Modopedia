package net.favouriteless.modopedia.api.books;

import net.favouriteless.modopedia.book.BookContentManagerImpl;
import net.minecraft.resources.ResourceLocation;

/**
 * BookContentManager maps all existing {@link BookContent}s to their book ID.
 */
public interface BookContentManager {

    static BookContentManager get() {
        return BookContentManagerImpl.INSTANCE;
    }

    BookContent getContent(ResourceLocation id);

    ResourceLocation getBookId(BookContent content);

    /**
     * Register (or overwrite) the content of a book.
     */
    void register(ResourceLocation id, BookContent content);

    /**
     * Remove all BookContents from the manager.
     */
    void clear();

}
