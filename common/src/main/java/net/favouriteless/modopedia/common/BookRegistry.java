package net.favouriteless.modopedia.common;

import net.favouriteless.modopedia.api.books.Book;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *     BookRegistry maps book IDs ({@link ResourceLocation}) to their {@link Book} instance. The registry will be
 *     populated on datapack reload.
 * </p>
 */
public class BookRegistry {

    private static final Map<ResourceLocation, Book> bookById = new HashMap<>();

    public static Book register(ResourceLocation id, Book book) {
        if(bookById.containsKey(id))
            throw new IllegalArgumentException("Attempted to register a duplicate Modopedia book: " + id.toString());
        bookById.put(id, book);

        return book;
    }

    @Nullable
    public static Book getBook(ResourceLocation id) {
        return bookById.get(id);
    }

    public static Collection<Book> getBooks() {
        return bookById.values();
    }

    /**
     * Danger danger don't call this unless you really mean it.
     */
    public static void clear() {
        bookById.clear();
    }

}
