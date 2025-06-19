package net.favouriteless.modopedia.api.book;

import com.mojang.serialization.Codec;
import net.favouriteless.modopedia.book.CategoryImpl;
import net.favouriteless.modopedia.book.text.TextChunk;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Second-highest level object of a book, acts as a way to organise {@link Entry}. Only exists client-side.
 */
public interface Category {

    /**
     * @return Title of the entry-- shown at the top of the landing page.
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
     * @return The {@link ItemStack} which is rendered as an icon for this entry.
     */
    ItemStack getIcon();

    /**
     * @return {@link List} of every Entry ID in this entry.
     */
    List<String> getEntries();

    /**
     * @return {@link List} of Category IDs which are inside this entry. These will be shown in the same
     * manner as entries, but always at the start of the list.
     */
    List<String> getChildren();

    /**
     * @return True if this entry is part of the front page of a book. Sub-categories generally set this to false.
     */
    boolean getDisplayOnFrontPage();

    /**
     * @return ID of the advancement needed to unlock this category.
     */
    ResourceLocation getAdvancement();

    static Codec<Category> codec() {
        return CategoryImpl.CODEC;
    }

}
