package net.favouriteless.modopedia.api.registries;

import net.favouriteless.modopedia.api.books.BookContent;
import net.favouriteless.modopedia.book.registries.BookContentRegistryImpl;
import net.minecraft.resources.ResourceLocation;

/**
 * Maps all existing {@link BookContent}s to their book ID. Will only be populated client-side.
 */
public interface BookContentRegistry {

    static BookContentRegistry get() {
        return BookContentRegistryImpl.INSTANCE;
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
     * Register (or overwrite) the content of a book. Will also re-register item associations for the book.
     */
    void register(ResourceLocation id, BookContent content);

    /**
     * Remove the contents of a specific book from the manager.
     *
     * @param id The ID of the book to be removed.
     */
    void remove(ResourceLocation id);

}
