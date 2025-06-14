package net.favouriteless.modopedia.api.book;

import com.mojang.serialization.Codec;
import net.favouriteless.modopedia.api.registries.common.BookRegistry;
import net.favouriteless.modopedia.book.BookImpl;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import org.jetbrains.annotations.Nullable;

/**
 * <p>
 *     The highest level object of a book, and the only part of it which exists server-side.
 * </p>
 * <p>
 *     Can be obtained via {@link BookRegistry#getBook(ResourceLocation)}.
 * </p>
 */
public interface Book {

    /**
     * @return {@link BookType} instance belonging to this book.
     */
    BookType getType();

    /**
     * @return Title of this book-- this will be the item's name and rendered at the top of the landing page.
     */
    String getTitle();

    /**
     * @return Subtitle of this book-- this is showed on the book's tooltip and under the title on the landing page.
     */
    @Nullable String getSubtitle();

    /**
     * @return The raw, unformatted landing text on this book (containing formatting tags etc.)
     */
    @Nullable String getRawLandingText();

    /**
     * @return ID for this book's {@link BookTexture}.
     */
    ResourceLocation getTexture();

    /**
     * @return {@link ResourceLocation} pointing to the model this book's item should use.
     */
    ResourceLocation getItemModelLocation();

    /**
     * @return ID of this book's open SoundEvent.
     */
    Holder<SoundEvent> getOpenSound();

    /**
     * @return ID of this book's flip SoundEvent.
     */
    Holder<SoundEvent> getFlipSound();

    /**
     * @return The default font for all formatted text in this book.
     */
    ResourceLocation getFont();

    /**
     * @return The default colour for all formatted text in this book.
     */
    int getTextColour();

    /**
     * @return The default colour for all headers in this book.
     */
    int getHeaderColour();

    /**
     * @return The default line width for formatted text in this book.
     */
    int getLineWidth();

    /**
     * @return The ResourceKey of the creative mode tab this book should be in. Will also appear in the search tab.
     */
    @Nullable ResourceKey<CreativeModeTab> getCreativeTab();

    static Codec<Book> persistentCodec() {
        return BookImpl.PERSISTENT_CODEC;
    }
    
    static StreamCodec<RegistryFriendlyByteBuf, Book> streamCodec() {
        return BookImpl.STREAM_CODEC;
    }

}
