package net.favouriteless.modopedia.api;

import net.favouriteless.modopedia.ModopediaApiImpl;
import net.favouriteless.modopedia.api.books.Book;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * Main API interface for Modopedia
 */
public interface ModopediaApi {

    static ModopediaApi get() {
        return ModopediaApiImpl.INSTANCE;
    }

    /**
     * @return True if Modopedia has finished loading book contents. False when resources start loading
     * (or reloading) and set to true when they have finished being registered.
     */
    boolean bookContentsLoaded();

    /**
     * Grab a {@link Book} by its ID. (Forwards call to {@link BookRegistry#getBook(ResourceLocation)})
     *
     * @param id {@link ResourceLocation} for the book-- this will be determined by its location in the datapack.
     *
     * @return Book matching id, or null if none were found.
     */
    @Nullable Book getBook(ResourceLocation id);

}
