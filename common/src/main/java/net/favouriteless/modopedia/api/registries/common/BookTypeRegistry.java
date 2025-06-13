package net.favouriteless.modopedia.api.registries.common;

import com.mojang.serialization.Codec;
import net.favouriteless.modopedia.api.book.BookType;
import net.favouriteless.modopedia.book.registries.common.BookTypeRegistryImpl;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * Maps {@link BookType}s to their IDs.
 */
public interface BookTypeRegistry {

    static BookTypeRegistry get() {
        return BookTypeRegistryImpl.INSTANCE;
    }

    /**
     * Register a new {@link BookType}. Duplicates are not allowed.
     */
    void register(BookType.Type<?> type);

    @Nullable BookType.Type<?> getType(ResourceLocation id);

    /**
     * @return The main {@link BookType} dispatch codec.
     */
    Codec<BookType> codec();

}
