package net.favouriteless.modopedia.multiblock.state_matchers;

import com.mojang.serialization.MapCodec;
import net.favouriteless.modopedia.api.multiblock.StateMatcher;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class AirStateMatcher implements StateMatcher {

    public static final MapCodec<AirStateMatcher> CODEC = MapCodec.unit(AirStateMatcher::new);

    public AirStateMatcher() {
    }

    @Override
    public boolean matches(BlockState state) {
        return state.isAir();
    }

    @Override
    public List<BlockState> getDisplayStates() {
        return List.of();
    }

    @Override
    public MapCodec<? extends StateMatcher> codec() {
        return CODEC;
    }

}
