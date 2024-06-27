package net.favouriteless.modopedia.api;

import net.favouriteless.modopedia.ModopediaApiImpl;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.text.TextFormatterRegistry;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

/**
 * Main API interface for Modopedia
 */
public interface ModopediaApi {

    static ModopediaApi get() {
        return ModopediaApiImpl.INSTANCE;
    }

    /**
     * @return Modopedia's {@link PageComponentRegistry} instance.
     */
    PageComponentRegistry getComponentRegistry();

    /**
     * @return Modopedia's {@link TextFormatterRegistry} instance.
     */
    TextFormatterRegistry getTextFormatterRegistry();

    /**
     * @return True if Modopedia has finished loading books.
     */
    boolean booksLoaded();

    /**
     * Grab a {@link Book} by its ID.
     *
     * @param location {@link ResourceLocation} for the book-- this will be determined by its location in the datapack.
     *
     * @return Book matching location, or null if none were found.
     */
    @Nullable Book getBook(ResourceLocation location);

}
