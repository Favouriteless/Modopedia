package net.favouriteless.modopedia.api;

import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.book.BookRegistryImpl;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * <p>
 *     BookRegistry maps book IDs to their book objects. This will be populated on both the client and server.
 * </p>
 * <p>
 *     Using the registry directly should be avoided where possible as Modopedia will repopulate it when
 *     datapacks reload.
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
     * @return Book registered under id, otherwise null if none were found.
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

    /**
     * Removes all registered books. Danger: do not call unless you really mean it.
     */
    void clear();

}
