package net.favouriteless.modopedia.api.books;

import com.mojang.serialization.Codec;
import net.favouriteless.modopedia.book.CategoryImpl;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Interface representing a category or chapter within a book.
 */
public interface Category {

    /**
     * @return The {@link Book} this category belongs to.
     */
    Book getBook();

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
    @Nullable Component getLandingText();
    
    /**
     * @return The raw, unformatted landing text (containing formatting tags etc.)
     */
    @Nullable String getRawLandingText();

    /**
     * @return The {@link ItemStack} which is rendered as an icon for this category.
     */
    @Nullable ItemStack getIcon();

    /**
     * @return The texture override for this category-- if not null, all entries in the category will
     * default to this texture.
     */
    @Nullable ResourceLocation getTexture();

    /**
     * @return {@link List} of every {@link Entry} ID in this category.
     */
    List<String> getEntries();

    /**
     * Get the {@link Entry} matching id if it is part of this category.
     *
     * @param id {@link ResourceLocation} id representing the entry's datapack location.
     *
     * @return An {@link Entry} matching id if one is found, otherwise null.
     */
    @Nullable default Entry getEntry(String id) {
        return getBook().getEntry(id);
    }

    /**
     * @return {@link List} of Category IDs which are inside this category. These will be shown in the same
     * manner as entries, but always at the start of the list.
     */
    List<String> getChildren();

    /**
     * Get the {@link Category} matching id if it is a child of this category.
     *
     * @param id {@link ResourceLocation} id representing the category's datapack location.
     *
     * @return A category matching id if one is found, otherwise null.
     */
    @Nullable default Category getChild(String id) {
        return getBook().getCategory(id);
    }
    
    static Codec<Category> persistentCodec() {
        return CategoryImpl.PERSISTENT_CODEC;
    }
    
    static StreamCodec<RegistryFriendlyByteBuf, Category> streamCodec() {
        return CategoryImpl.STREAM_CODEC;
    }

}
