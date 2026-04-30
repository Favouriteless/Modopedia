package net.favouriteless.modopedia.api.multiblock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.favouriteless.modopedia.api.registries.client.StateMatcherRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * Used to determine if a {@link BlockState} matches a set of conditions for multiblocks.
 */
public interface StateMatcher {

    /**
     * @return The main {@link StateMatcher} dispatch codec.
     */
    static Codec<StateMatcher> codec() {
        return StateMatcherRegistry.get().codec();
    }

    /**
     * @return True if the given block matches.
     */
    boolean matches(Block block);

    /**
     * @return True if the given state matches.
     */
    boolean matches(BlockState state);

    /**
     * @return A list of {@link BlockState}s this matcher can display.
     */
    List<BlockState> getDisplayStates();

    /**
     * @return {@link MapCodec} for this type of StateMatcher.
     */
    MapCodec<? extends StateMatcher> typeCodec();

}
