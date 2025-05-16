package net.favouriteless.modopedia.api.books;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.favouriteless.modopedia.api.BookRegistry;
import net.favouriteless.modopedia.book.BookImpl;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * Interface representing an entire book-- can be obtained with {@link BookRegistry#getBook(ResourceLocation)}
 */
public interface Book {

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
