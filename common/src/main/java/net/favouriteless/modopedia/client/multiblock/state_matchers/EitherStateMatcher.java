package net.favouriteless.modopedia.client.multiblock.state_matchers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.multiblock.StateMatcher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

/**
 * Special state matcher which returns true if either A or B match
 */
public class EitherStateMatcher implements StateMatcher {

    public static final ResourceLocation ID = Modopedia.id("either");

    public static final MapCodec<EitherStateMatcher> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.lazyInitialized(StateMatcher::codec).fieldOf("a").forGetter(m -> m.a),
            Codec.lazyInitialized(StateMatcher::codec).fieldOf("b").forGetter(m -> m.b)
    ).apply(instance, EitherStateMatcher::new));

    private final StateMatcher a;
    private final StateMatcher b;
    private final List<BlockState> displayStates = new ArrayList<>();

    public EitherStateMatcher(StateMatcher a, StateMatcher b) {
        this.a = a;
        this.b = b;
        displayStates.addAll(a.getDisplayStates());
        displayStates.addAll(b.getDisplayStates());
    }

    @Override
    public boolean matches(BlockState state) {
        return a.matches(state) || b.matches(state);
    }

    @Override
    public List<BlockState> getDisplayStates() {
        return displayStates;
    }

    @Override
    public MapCodec<? extends StateMatcher> typeCodec() {
        return CODEC;
    }

}
