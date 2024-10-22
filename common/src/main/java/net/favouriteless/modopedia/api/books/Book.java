package net.favouriteless.modopedia.api.books;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.favouriteless.modopedia.api.ModopediaApi;
import net.favouriteless.modopedia.book.BookImpl;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * Interface representing an entire book-- can be obtained with {@link ModopediaApi#getBook(ResourceLocation)}
 */
public interface Book {

    /**
     * @return The ID of this book (defined in the datapack book.json)
     */
    ResourceLocation getId();

    /**
     * @return Type of this book (e.g. "modopedia:classic")
     */
    ResourceLocation getType();

    /**
     * @return Title of this book-- this will be the item's name and showed at the top of the landing page.
     */
    String getTitle();

    /**
     * @return Subtitle of this book-- this is showed on the book's tooltip and under the title on the landing page.
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
     * @return The language code this book defaults to when attempting to find entries and categories. en_us by default.
     */
    String getDefaultLanguageCode();

    /**
     * Get the {@link Category} matching ID if it is part of this book. If one of the specified localisation cannot
     * be found, it will default to en_us.
     *
     * @param languageCode ID for the language (See: {@link LanguageManager#languages})
     * @param id ID representing the category's resource pack location.
     *
     * @return A category matching id if one is found, otherwise null.
     */
    @Nullable Category getCategory(String languageCode, String id);

    /**
     * Get the {@link Entry} matching ID if it is part of this book. If one of the specified localisation cannot
     * be found, it will default to en_us.
     *
     * @param languageCode ID for the language (See: {@link LanguageManager#languages})
     * @param id ID representing the entry's resource pack location.
     *
     * @return An {@link Entry} matching id if one is found, otherwise null.
     */
    @Nullable Entry getEntry(String languageCode, String id);

    /**
     * Get the {@link Category} matching ID if it is part of this book. Defaults to en_us localisation.
     */
    @Nullable default Category getCategory(String id) {
        return getCategory("en_us", id);
    }

    /**
     * Get the {@link Entry} matching ID if it is part of this book. Defaults to en_us localisation.
     */
    @Nullable default Entry getEntry(String id) {
        return getEntry("en_us", id);
    }

    static Codec<Book> persistentCodec() {
        return BookImpl.PERSISTENT_CODEC;
    }
    
    static StreamCodec<ByteBuf, Book> streamCodec() {
        return BookImpl.STREAM_CODEC;
    }

}
