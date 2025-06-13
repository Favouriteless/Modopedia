package net.favouriteless.modopedia.api.book;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.favouriteless.modopedia.api.registries.common.BookTypeRegistry;
import net.favouriteless.modopedia.client.screens.books.ClassicLandingScreen;
import net.minecraft.resources.ResourceLocation;

/**
 * <p>
 *     A BookScreenFactory represents a single "type" of book by providing the methods used to open its Screens.
 * </p>
 * <p>
 *     See {@link ClassicLandingScreen} for an example.
 * </p>
 */
public interface BookType {

    /**
     * @return The main {@link BookType} dispatch codec.
     */
    static Codec<BookType> codec() {
        return BookTypeRegistry.get().codec();
    }

    /**
     * @return {@link Type} for this type of BookType.
     */
    Type<?> type();


    record Type<T extends BookType>(ResourceLocation id, MapCodec<T> codec) {}

}