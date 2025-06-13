package net.favouriteless.modopedia.api.registries;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.favouriteless.modopedia.api.multiblock.Multiblock;
import net.favouriteless.modopedia.api.multiblock.StateMatcher;
import net.favouriteless.modopedia.multiblock.MultiblockRegistryImpl;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * Maps all existing {@link Multiblock} IDs to their instances, and {@link StateMatcher} or multiblock types to their
 * codecs.
 */
public interface MultiblockRegistry {

    static MultiblockRegistry get() {
        return MultiblockRegistryImpl.INSTANCE;
    }

    /**
     * Register a new {@link Multiblock}. Will override existing registry entries.
     */
    void register(ResourceLocation id, Multiblock multiblock);

    /**
     * @return The {@link Multiblock} registered under book, otherwise null.
     */
    @Nullable Multiblock get(ResourceLocation id);

    /**
     * Register a new {@link Multiblock} codec. Duplicates not allowed.
     */
    void registerType(ResourceLocation id, MapCodec<? extends Multiblock> codec);

    /**
     * @return The {@link Multiblock} codec registered under book, otherwise null.
     */
    @Nullable MapCodec<? extends Multiblock> getType(ResourceLocation id);

    /**
     * @return The main {@link Multiblock} dispatch codec.
     */
    Codec<Multiblock> codec();

}
