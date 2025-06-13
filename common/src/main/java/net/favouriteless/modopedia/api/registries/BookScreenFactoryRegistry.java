package net.favouriteless.modopedia.api.registries;

import net.favouriteless.modopedia.api.books.BookScreenFactory;
import net.favouriteless.modopedia.api.books.BookType;
import net.favouriteless.modopedia.api.books.BookType.Type;
import net.favouriteless.modopedia.book.registries.client.BookScreenFactoryRegistryImpl;
import org.jetbrains.annotations.Nullable;

/**
 * Maps {@link BookScreenFactory}s to their IDs.
 */
public interface BookScreenFactoryRegistry {

    static BookScreenFactoryRegistry get() {
        return BookScreenFactoryRegistryImpl.INSTANCE;
    }

    /**
     * Register a new {@link BookScreenFactory}. Duplicates are not allowed.
     */
    <T extends BookType> void register(Type<T> type, BookScreenFactory<T> factory);

    @Nullable <T extends BookType> BookScreenFactory<T> get(T type);

}
