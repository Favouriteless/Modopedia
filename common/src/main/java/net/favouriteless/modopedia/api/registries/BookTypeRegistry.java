package net.favouriteless.modopedia.api.registries;

import net.favouriteless.modopedia.api.books.BookType;
import net.favouriteless.modopedia.book.registries.BookTypeRegistryImpl;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * Maps {@link BookType}s to their IDs.
 */
public interface BookTypeRegistry {

    static BookTypeRegistry get() {
        return BookTypeRegistryImpl.INSTANCE;
    }

    /**
     * Register a new {@link BookType}. Duplicates are not allowed.
     */
    void register(ResourceLocation id, BookType factory);

    @Nullable BookType getType(ResourceLocation id);

}
