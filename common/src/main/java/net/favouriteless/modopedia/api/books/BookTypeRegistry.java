package net.favouriteless.modopedia.api.books;

import net.favouriteless.modopedia.client.BookTypeRegistryImpl;
import net.favouriteless.modopedia.client.screens.BookScreen;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * BookTypeRegistry maps the type field in book.jsons to a screen factory, so the book item opens the correct
 * screen.
 */
public interface BookTypeRegistry {

    static BookTypeRegistry get() {
        return BookTypeRegistryImpl.INSTANCE;
    }

    /**
     * Register a new screen factory for the given book type.
     */
    void register(ResourceLocation id, Function<Book, BookScreen> factory);

    /**
     * Attempts to create a new {@link BookScreen} for the given book.
     *
     * @return A BookScreen if book exists and has a valid type, otherwise null.
     */
    @Nullable BookScreen getScreen(Book book);

    /**
     * Attempt to grab the screen factory for a given book ID.
     *
     * @return Factory if one exists, otherwise null.
     */
    @Nullable Function<Book, BookScreen> getFactory(ResourceLocation id);

}
