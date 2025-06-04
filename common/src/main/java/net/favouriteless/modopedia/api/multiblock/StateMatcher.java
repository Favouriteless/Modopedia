package net.favouriteless.modopedia.api.multiblock;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * Used to determine if a {@link BlockState} matches a set of conditions for multiblocks.
 */
public interface StateMatcher {

    default boolean matches(BlockGetter level, BlockPos pos) {
        return matches(level.getBlockState(pos));
    }

    /**
     * @return true if the given state matches.
     */
    boolean matches(BlockState state);

    List<BlockState> getDisplayStates();

    MapCodec<? extends StateMatcher> codec();

}
