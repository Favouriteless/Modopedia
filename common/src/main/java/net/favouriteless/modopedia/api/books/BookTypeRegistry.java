package net.favouriteless.modopedia.api.books;

import net.favouriteless.modopedia.client.BookTypeRegistryImpl;
import net.favouriteless.modopedia.client.screens.BookScreen;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * BookTypeRegistry handles mapping the type field in book.jsons to a screen factory, so the book item opens the correct
 * screen.
 */
public interface BookTypeRegistry {

    static BookTypeRegistry get() {
        return BookTypeRegistryImpl.INSTANCE;
    }

    void register(ResourceLocation id, Function<Book, BookScreen> factory);

    @Nullable BookScreen getScreen(Book book);

    @Nullable Function<Book, BookScreen> getFactory(ResourceLocation id);

}
