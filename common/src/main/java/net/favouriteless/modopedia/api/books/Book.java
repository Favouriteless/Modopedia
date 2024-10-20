package net.favouriteless.modopedia.api.books;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.favouriteless.modopedia.book.BookImpl;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

/**
 * Interface representing an entire book-- these can be grabbed by their {@link ResourceLocation} ID.
 */
public interface Book {

    /**
     * @return Type of the book (e.g. "modopedia:classic")
     */
    ResourceLocation getType();

    /**
     * @return Title of the book-- this will be the item's name and showed at the top of the landing page.
     */
    String getTitle();

    /**
     * @return Subtitle of the book-- this is showed on the book's tooltip and under the title on the landing page.
     */
    @Nullable String getSubtitle();

    /**
     * @return Landing text (the text displayed on the title page).
     */
    @Nullable Component getLandingText();

    /**
     * @return The raw, unformatted landing text (containing formatting tags etc.)
     */
    @Nullable String getRawLandingText();

    /**
     * @return {@link ResourceLocation} pointing to the default texture used for this book's {@link Screen}.
     */
    ResourceLocation getTexture();

    /**
     * @return {@link ResourceLocation} pointing to the model this book's item should use.
     */
    ResourceLocation getItemModelLocation();

    /**
     * Get the {@link Category} matching id if it is part of this book.
     *
     * @param id {@link ResourceLocation} id representing the category's datapack location.
     *
     * @return A category matching id if one is found, otherwise null.
     */
    @Nullable Category getCategory(String id);

    /**
     * Get the {@link Entry} matching id if it is part of this book.
     *
     * @param id {@link ResourceLocation} id representing the entry's datapack location.
     *
     * @return An {@link Entry} matching id if one is found, otherwise null.
     */
    @Nullable Entry getEntry(String id);

    static Codec<Book> persistentCodec() {
        return BookImpl.PERSISTENT_CODEC;
    }
    
    static StreamCodec<ByteBuf, Book> streamCodec() {
        return BookImpl.STREAM_CODEC;
    }

}
