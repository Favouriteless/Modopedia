package net.favouriteless.modopedia.api.registries;

import net.favouriteless.modopedia.api.books.BookTexture;
import net.favouriteless.modopedia.book.registries.BookTextureRegistryImpl;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * Maps {@link BookTexture}s to their IDs.
 */
public interface BookTextureRegistry {

    static BookTextureRegistry get() {
        return BookTextureRegistryImpl.INSTANCE;
    }

    /**
     * Register a new BookTexture. Duplicates are not allowed.
     */
    void register(ResourceLocation id, BookTexture texture);

    @Nullable BookTexture getTexture(ResourceLocation id);

}
