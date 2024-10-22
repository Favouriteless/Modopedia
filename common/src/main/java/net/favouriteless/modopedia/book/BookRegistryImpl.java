package net.favouriteless.modopedia.book;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.netty.buffer.ByteBuf;
import net.favouriteless.modopedia.api.BookRegistry;
import net.favouriteless.modopedia.api.books.Book;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class BookRegistryImpl implements BookRegistry {

    public static final BookRegistry INSTANCE = new BookRegistryImpl();

    private final BiMap<ResourceLocation, Book> books = HashBiMap.create();

    private BookRegistryImpl() {}

    public void register(ResourceLocation id, Book book) {
        if(books.containsKey(id))
            throw new IllegalArgumentException("Attempted to register a duplicate Modopedia book: " + id.toString());
        books.put(id, book);
    }

    @Override
    @Nullable
    public Book getBook(ResourceLocation id) {
        return books.get(id);
    }

    @Override
    @Nullable
    public ResourceLocation getId(Book book) {
        return books.inverse().get(book);
    }

    @Override
    public Collection<Book> getBooks() {
        return books.values();
    }

    /**
     * Danger danger don't call this unless you really mean it.
     */
    public void clear() {
        books.clear();
    }

}
