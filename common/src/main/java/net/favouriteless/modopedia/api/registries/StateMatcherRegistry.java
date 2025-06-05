package net.favouriteless.modopedia.api.registries;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.favouriteless.modopedia.api.multiblock.StateMatcher;
import net.favouriteless.modopedia.multiblock.state_matchers.StateMatcherRegistryImpl;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * Maps all existing {@link StateMatcher} IDs to their codec.
 */
public interface StateMatcherRegistry {

    static StateMatcherRegistry get() {
        return StateMatcherRegistryImpl.INSTANCE;
    }

    /**
     * Register a new {@link StateMatcher} codec. Duplicates not allowed.
     */
    void register(ResourceLocation id, MapCodec<? extends StateMatcher> codec);

    @Nullable MapCodec<? extends StateMatcher> get(ResourceLocation id);

    /**
     * @return The main {@link StateMatcher} dispatch codec.
     */
    Codec<StateMatcher> codec();

}
