package net.favouriteless.modopedia.client.multiblock.state_matchers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.multiblock.StateMatcher;
import net.favouriteless.modopedia.client.multiblock.BlockStateCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public record SimpleStateMatcher(List<BlockState> states) implements StateMatcher {

    public static final ResourceLocation ID = Modopedia.id("simple");

    public static final MapCodec<SimpleStateMatcher> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockStateCodec.CODEC.listOf().fieldOf("states").forGetter(SimpleStateMatcher::states)
    ).apply(instance, SimpleStateMatcher::new));

    @Override
    public boolean matches(Block block) {
        for(BlockState s : states) {
            if(s.getBlock() == block)
                return true;
        }
        return false;
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
        return CODEC;
    }

}
