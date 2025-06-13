package net.favouriteless.modopedia.api.book;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

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
    ItemStack getIcon();
    
    /**
     * @return {@link List} of all {@link Page}s within this Entry, these are what actually get rendered when the entry
     * is opened.
     */
    List<Page> getPages();

    /**
     * @return The item IDs "assigned" to this entry. For the ponder feature.
     */
    List<ResourceLocation> getAssignedItems();
    
}
