package net.favouriteless.modopedia.api;

import net.favouriteless.modopedia.api.books.BookTexture;
import net.favouriteless.modopedia.client.BookTextureRegistryImpl;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * BookTypeRegistry maps book textures to their PageDetails.
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

    /**
     * Clears the registry. Only call this if you have a very good reason to.
     */
    void clear();

}
