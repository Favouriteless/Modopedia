package net.favouriteless.modopedia.api.registries.client;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.favouriteless.modopedia.api.book.page_components.ItemDisplay;
import net.favouriteless.modopedia.api.multiblock.StateMatcher;
import net.favouriteless.modopedia.book.registries.client.ItemDisplayRegistryImpl;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * Maps all existing {@link ItemDisplay} IDs to their codec.
 */
public interface ItemDisplayRegistry {

    static ItemDisplayRegistry get() {
        return ItemDisplayRegistryImpl.INSTANCE;
    }

    /**
     * Register a new {@link StateMatcher} codec. Duplicates not allowed.
     */
    void register(ResourceLocation id, MapCodec<? extends ItemDisplay> codec);

    @Nullable MapCodec<? extends ItemDisplay> get(ResourceLocation id);

    /**
     * @return The main {@link StateMatcher} dispatch codec.
     */
    Codec<ItemDisplay> codec();

}
