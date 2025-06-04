package net.favouriteless.modopedia.multiblock.state_matchers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.api.multiblock.StateMatcher;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class TagStateMatcher implements StateMatcher {

    public static final MapCodec<TagStateMatcher> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            TagKey.codec(Registries.BLOCK).fieldOf("tag").forGetter(TagStateMatcher::getTag)
    ).apply(instance, TagStateMatcher::new));

    private final TagKey<Block> tag;
    private final List<BlockState> displayStates;

    public TagStateMatcher(TagKey<Block> tag) {
        this.tag = tag;
        this.displayStates = BuiltInRegistries.BLOCK.getTag(tag)
                .map(holders -> holders.stream().map(h -> h.value().defaultBlockState()).toList())
                .orElseGet(List::of);
    }

    @Override
    public boolean matches(BlockState state) {
        return BuiltInRegistries.BLOCK.getTag(tag)
                .map(holders -> holders.contains(state.getBlockHolder()))
                .orElse(false);
    }

    @Override
    public List<BlockState> getDisplayStates() {
        return displayStates;
    }

    @Override
    public MapCodec<? extends StateMatcher> codec() {
        return CODEC;
    }

    public TagKey<Block> getTag() {
        return tag;
    }

}
