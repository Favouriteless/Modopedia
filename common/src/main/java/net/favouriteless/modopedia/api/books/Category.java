package net.favouriteless.modopedia.api.books;

import net.favouriteless.modopedia.book.text.TextChunk;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Second-highest level object of a book, acts as a way to organise {@link Entry}. Only exists client-side.
 */
public interface Category {

    /**
     * @return Title of the category-- shown at the top of the landing page.
     */
    String getTitle();

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
    ItemStack getIcon();

    /**
     * @return {@link List} of every Entry ID in this category.
     */
    List<String> getEntries();

    /**
     * @return {@link List} of Category IDs which are inside this category. These will be shown in the same
     * manner as entries, but always at the start of the list.
     */
    List<String> getChildren();

    /**
     * @return True if this category is part of the front page of a book. Sub-categories generally set this to false.
     */
    boolean getDisplayOnFrontPage();

}
