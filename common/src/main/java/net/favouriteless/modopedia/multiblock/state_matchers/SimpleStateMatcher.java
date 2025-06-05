package net.favouriteless.modopedia.multiblock.state_matchers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.api.multiblock.StateMatcher;
import net.favouriteless.modopedia.multiblock.BlockStateCodec;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class SimpleStateMatcher implements StateMatcher {

    public static final MapCodec<SimpleStateMatcher> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockStateCodec.CODEC.listOf().fieldOf("states").forGetter(SimpleStateMatcher::getStates)
    ).apply(instance, SimpleStateMatcher::new));

    private final List<BlockState> states;

    public SimpleStateMatcher(List<BlockState> state) {
        this.states = state;
    }

    @Override
    public boolean matches(BlockState state) {
        for(BlockState s : states) {
            if(s.equals(state))
                return true;
        }
        return false;
    }

    @Override
    public List<BlockState> getDisplayStates() {
        return states;
    }

    @Override
    public MapCodec<? extends StateMatcher> typeCodec() {
        return typeCodec();
    }

    public List<BlockState> getStates() {
        return states;
    }

}
