package net.favouriteless.modopedia.api.books;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.favouriteless.modopedia.api.registries.BookRegistry;
import net.favouriteless.modopedia.book.BookImpl;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
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
     * @return ID for this book's {@link BookType}.
     */
    ResourceLocation getType();

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

    static Codec<Book> persistentCodec() {
        return BookImpl.PERSISTENT_CODEC;
    }
    
    static StreamCodec<ByteBuf, Book> streamCodec() {
        return BookImpl.STREAM_CODEC;
    }

}
