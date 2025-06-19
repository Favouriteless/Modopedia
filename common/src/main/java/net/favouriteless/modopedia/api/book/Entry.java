package net.favouriteless.modopedia.api.book;

import com.mojang.serialization.Codec;
import net.favouriteless.modopedia.book.EntryImpl;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * The lowest level object in a book, containing user generated content. Only exists client-side.
 */
public interface Entry {

    /**
     * @return The title of this entry, displayed in the entries list of Categories this entry is a part of.
     */
    String getTitle();
    
    /**
     * @return The {@link ItemStack} icon for this entry, displayed in the entries list of Categories this entry is a
     * part of.
     */
    @Nullable ItemStack getIcon();
    
    /**
     * @return {@link List} of all {@link Page}s within this Entry, these are what actually get rendered when the entry
     * is opened.
     */
    List<Page> getPages();

    /**
     * @return The item IDs "assigned" to this entry. For the ponder feature.
     */
    List<ResourceLocation> getAssignedItems();

    /**
     * @return ID of the advancement needed to unlock this entry.
     */
    @Nullable ResourceLocation getAdvancement();

    static Codec<Entry> codec() {
        return EntryImpl.CODEC;
    }

}
