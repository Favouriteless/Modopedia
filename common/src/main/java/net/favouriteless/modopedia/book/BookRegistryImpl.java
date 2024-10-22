package net.favouriteless.modopedia.book;

import net.favouriteless.modopedia.api.BookRegistry;
import net.favouriteless.modopedia.api.books.Book;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BookRegistryImpl implements BookRegistry {

    public static final BookRegistry INSTANCE = new BookRegistryImpl();

    private final Map<ResourceLocation, Book> bookById = new HashMap<>();

    private BookRegistryImpl() {}

    public void register(ResourceLocation id, Book book) {
        if(bookById.containsKey(id))
            throw new IllegalArgumentException("Attempted to register a duplicate Modopedia book: " + id.toString());
        bookById.put(id, book);
    }

    @Override
    @Nullable
    public Book getBook(ResourceLocation id) {
        return bookById.get(id);
    }

    @Override
    public Collection<Book> getBooks() {
        return bookById.values();
    }

    /**
     * Danger danger don't call this unless you really mean it.
     */
    public void clear() {
        bookById.clear();
    }

}
