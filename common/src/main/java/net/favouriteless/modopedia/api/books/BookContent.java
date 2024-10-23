package net.favouriteless.modopedia.api.books;

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
     * Fetch an Entry by its ID and a given language code. If no match is found, will attempt to find the same entry
     * with the book's default language.
     *
     * @param id ID of the target entry.
     * @param language Language code to target. (e.g. en_us)
     *
     * @return Entry found by id and language, or null if none were found.
     */
    @Nullable Entry getEntry(String id, String language);

    @Nullable Entry getEntry(String id);

    /**
     * Fetch a Category by its ID and a given language code. If no match is found, will attempt to find the same
     * category with the book's default language.
     *
     * @param id ID of the target category.
     * @param language Language code to target. (e.g. en_us)
     *
     * @return Category found by id and language, or null if none were found.
     */
    @Nullable Category getCategory(String id, String language);

    @Nullable Category getCategory(String id);

    /**
     * @return A collection of all entries under the given language code.
     */
    Collection<Entry> getEntries(String language);

    /**
     * @return A collection of all categories under the given language code.
     */
    Collection<Category> getCategories(String language);

}
