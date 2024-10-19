package net.favouriteless.modopedia.common;

import net.favouriteless.modopedia.api.books.Book;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *     BookRegistry maps book IDs ({@link ResourceLocation}) to their {@link Book} instance. The registry will be
 *     populated on datapack reload by {@link BookReloadListener}.
 * </p>
 */
public class BookRegistry {

    public static final BookRegistry INSTANCE = new BookRegistry();

    private final Map<ResourceLocation, Book> bookById = new HashMap<>();

    private BookRegistry() {

    }

    public Book register(ResourceLocation id, Book book) {
        if(bookById.containsKey(id))
            throw new IllegalArgumentException("Attempted to register a duplicate modopedia book: " + id.toString());
        bookById.put(id, book);

        return book;
    }

    @Nullable
    public Book getBook(ResourceLocation id) {
        return bookById.get(id);
    }

    public void clear() {
        bookById.clear();
    }

}
