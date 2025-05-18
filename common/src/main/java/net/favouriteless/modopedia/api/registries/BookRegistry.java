package net.favouriteless.modopedia.api.registries;

import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.common.BookRegistryImpl;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * <p>
 *     Maps book IDs to their {@link Book} objects. This will be populated on both the client and server.
 * </p>
 * <p>
 *     Modopedia will repopulate the registry when datapacks reload.
 * </p>
 */
public interface BookRegistry {

    static BookRegistry get() {
        return BookRegistryImpl.INSTANCE;
    }

    /**
     * Register a new book.
     *
     * @param id ID to register the book under.
     * @param book The actual {@link Book} object.
     */
    void register(ResourceLocation id, Book book);

    /**
     * @return {@link Book} registered under id, or null if none were found.
     */
    @Nullable Book getBook(ResourceLocation id);

    /**
     * @return ID of book, or null if none were found.
     */
    @Nullable ResourceLocation getId(Book book);

    /**
     * @return A collection containing all registered Books.
     */
    Collection<Book> getBooks();

    /**
     * @return A collection containing all registered book IDs
     */
    Collection<ResourceLocation> getBookIds();

}
