package net.favouriteless.modopedia.api.books;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Interface representing an entry (a group of templates/pages) in a book.
 */
public interface Entry {

    /**
     * @return The {@link Book} this category belongs to.
     */
    Book getBook();

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
     * @return {@link List} of all pages within this Entry, these are what actually get rendered when the entry is
     * opened.
     */
    List<Page> getPages();
    
}
