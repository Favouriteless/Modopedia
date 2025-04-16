package net.favouriteless.modopedia.api.books;

import net.favouriteless.modopedia.api.BookContentManager;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * <p>
 *     BookContent holds all categories and entries within a book, separated by their localisation.
 * </p>
 * <p>
 *     Can be obtained via {@link BookContentManager#getContent(ResourceLocation)}.
 * </p>
 */
public interface BookContent {

    /**
     * Fetch an Entry by its ID and language code.
     *
     * @param id ID of the target entry.
     * @param language Language code to target. (e.g. en_us)
     *
     * @return Entry found by id and language, or null if none were found.
     */
    @Nullable Entry getEntry(String id, String language);

    /**
     * Fetch a Category by its ID and language code.
     *
     * @param id ID of the target category.
     * @param language Language code to target. (e.g. en_us)
     *
     * @return Category found by id and language, or null if none were found.
     */
    @Nullable Category getCategory(String id, String language);

    /**
     * @param language Language code to target. (e.g. en_us)
     *
     * @return The {@link LocalisedBookContent} for the specified language. If the selected language does not exist,
     * try en_us. If en_us does not exist pick the first element, or null if the book is empty.
     */
    @Nullable LocalisedBookContent getLocalisedContent(String language);

    /**
     * @return A collection of all {@link LocalisedBookContent}s in this book.
     */
    Collection<LocalisedBookContent> getLocalisedContents();



    /**
     * Represents a set of categories and entries in a book of a single language or localisation.
     */
    interface LocalisedBookContent {

        @Nullable Category getCategory(String id);

        @Nullable Entry getEntry(String id);

    }

}
