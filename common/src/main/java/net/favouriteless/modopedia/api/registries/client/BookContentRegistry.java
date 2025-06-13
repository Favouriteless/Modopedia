package net.favouriteless.modopedia.api.registries.client;

import net.favouriteless.modopedia.api.book.BookContent;
import net.favouriteless.modopedia.api.book.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.book.registries.client.BookContentRegistryImpl;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * Maps all existing {@link BookContent}s to their book ID. Will only be populated client-side.
 */
public interface BookContentRegistry {

    static BookContentRegistry get() {
        return BookContentRegistryImpl.INSTANCE;
    }

    /**
     * @return The content for the given book ID.
     */
    @Nullable BookContent getContent(ResourceLocation id);

    @Nullable LocalisedBookContent getContent(ResourceLocation id, String lang);

    /**
     * @return The book ID the given content belongs to.
     */
    @Nullable ResourceLocation getBookId(BookContent content);

    /**
     * Register (or overwrite) the content of a book. Will also re-register item associations for the book.
     */
    void register(ResourceLocation id, BookContent content);

}
