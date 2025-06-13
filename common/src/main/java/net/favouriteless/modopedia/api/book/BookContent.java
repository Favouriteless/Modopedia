package net.favouriteless.modopedia.api.book;

import net.favouriteless.modopedia.api.registries.client.BookContentRegistry;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * <p>
 *     Holds all categories and entries within a book, separated by their localisation.
 * </p>
 * <p>
 *     Can be obtained via {@link BookContentRegistry#getContent(ResourceLocation)}.
 * </p>
 */
public interface BookContent {

    /**
     * @param language Language code to target. (e.g. en_us)
     *
     * @return The {@link LocalisedBookContent} for the specified language. If the selected language does not exist,
     * try en_us. If en_us does not exist pick the first element, or null if the book is empty.
     */
    @Nullable LocalisedBookContent getContent(String language);

    /**
     * @return A collection of all language codes this BookContent contains content for.
     */
    Collection<String> getLanguages();


    /**
     * Holds all {@link Category} and {@link Entry} for a single language code.
     */
    interface LocalisedBookContent {

        boolean hasEntry(String id);

        boolean hasCategory(String id);

        @Nullable Entry getEntry(String id);

        @Nullable Category getCategory(String id);

        Collection<String> getEntryIds();

        Collection<String> getCategoryIds();

    }

}
