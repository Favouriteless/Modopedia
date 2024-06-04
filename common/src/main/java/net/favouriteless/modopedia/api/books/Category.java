package net.favouriteless.modopedia.api.books;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Interface representing a category or chapter within a book.
 */
public interface Category {

    /**
     * @return Title of the category-- this will at the top of the landing page.
     */
    Component getTitle();

    /**
     * @return Subtitle of the category-- this will be under the title.
     */
    @Nullable Component getSubtitle();

    /**
     * @return The description shown underneath the title and subtitle.
     */
    @Nullable String getDescription();

    /**
     * @return The {@link ItemStack} which is rendered as an icon for this category.
     */
    ItemStack getIcon();

    /**
     * @return The texture override for this category-- if not null, all entries in the category will
     * default to this texture for the screen.
     */
    @Nullable ResourceLocation getDefaultTexture();

    /**
     * @return {@link List} of every {@link Entry} in this category.
     */
    List<Entry> getEntries();

    /**
     * @return {@link List} of {@link Category}s which are inside this category. These will be shown in the same
     * manner as entries, but always at the start of the list.
     */
    List<Category> getChildren();

}
