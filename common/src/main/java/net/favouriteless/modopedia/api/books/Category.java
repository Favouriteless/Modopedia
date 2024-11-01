package net.favouriteless.modopedia.api.books;

import net.favouriteless.modopedia.book.text.TextChunk;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Interface representing a category or chapter within a book.
 */
public interface Category {

    /**
     * @return Title of the category-- this will at the top of the landing page.
     */
    String getTitle();

    /**
     * @return Subtitle of the category-- this will be under the title.
     */
    @Nullable String getSubtitle();

    /**
     * @return The description shown underneath the title and subtitle.
     */
    @Nullable List<TextChunk> getLandingText();
    
    /**
     * @return The raw, unformatted landing text (containing formatting tags etc.)
     */
    @Nullable String getRawLandingText();

    /**
     * @return The {@link ItemStack} which is rendered as an icon for this category.
     */
    @Nullable ItemStack getIcon();

    /**
     * @return {@link List} of every Entry ID in this category.
     */
    List<String> getEntries();

    /**
     * @return {@link List} of Category IDs which are inside this category. These will be shown in the same
     * manner as entries, but always at the start of the list.
     */
    List<String> getChildren();

}
