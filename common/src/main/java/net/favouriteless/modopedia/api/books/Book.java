package net.favouriteless.modopedia.api.books;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Interface representing an entire book-- these can be grabbed by their {@link ResourceLocation} ID.
 */
public interface Book {

    /**
     * @return ID for this book, this is decided by the book.json's location.
     */
    ResourceLocation getId();

    /**
     * @return Type of the book (e.g. "modopedia:classic")
     */
    ResourceLocation getType();

    /**
     * @return Title of the book-- this will be the item's name and showed at the top of the landing page.
     */
    Component getTitle();

    /**
     * @return Subtitle of the book-- this is showed on the book's tooltip and under the title on the landing page.
     */
    @Nullable Component getSubtitle();

    /**
     * @return Landing text (the text displayed on the title page).
     */
    @Nullable String getLandingText();

    /**
     * @return {@link ResourceLocation} pointing to the default texture used for this book's {@link Screen}.
     */
    ResourceLocation getDefaultTexture();

    /**
     * @return {@link ResourceLocation} pointing to the model this book's item should use.
     */
    ResourceLocation getItemModelLocation();

    /**
     * @return {@link List} containing the {@link Category} instances in this book.
     */
    List<Category> getCategories();

}
