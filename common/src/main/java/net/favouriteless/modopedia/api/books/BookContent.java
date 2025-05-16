package net.favouriteless.modopedia.api.books;

import net.favouriteless.modopedia.api.BookContentRegistry;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * <p>
 *     BookContent holds all categories and entries within a book, separated by their localisation.
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
     * @return A collection of all {@link LocalisedBookContent}s in this book.
     */
    Collection<LocalisedBookContent> getAllContents();

    /**
     * @return A collection of all language codes this BookContent contains content for.
     */
    Collection<String> getLanguages();

    /**
     * Represents a set of categories and entries in a book of a single language or localisation.
     */
    interface LocalisedBookContent {

        @Nullable Category getCategory(String id);

        boolean hasCategory(String id);

        @Nullable Entry getEntry(String id);

        boolean hasEntry(String id);

        Collection<Entry> getEntries();

        Collection<Category> getCategories();

        Collection<String> getCategoryIds();

        Collection<String> getEntryIds();

    }

}
