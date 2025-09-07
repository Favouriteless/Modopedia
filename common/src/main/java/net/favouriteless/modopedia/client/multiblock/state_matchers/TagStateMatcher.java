package net.favouriteless.modopedia.client.multiblock.state_matchers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.multiblock.StateMatcher;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class TagStateMatcher implements StateMatcher {

    public static final ResourceLocation ID = Modopedia.id("tag");

    public static final MapCodec<TagStateMatcher> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            TagKey.codec(Registries.BLOCK).fieldOf("tag").forGetter(TagStateMatcher::getTag)
    ).apply(instance, TagStateMatcher::new));

    private final TagKey<Block> tag;
    private final Named<Block> named;
    private List<BlockState> displayStates;

    public TagStateMatcher(TagKey<Block> tag) {
        this.tag = tag;
        this.named = BuiltInRegistries.BLOCK.getTag(tag).orElse(null);
    }

    @Override
    public boolean matches(Block block) {
        return named.contains(block.builtInRegistryHolder());
    }

    @Override
    public boolean matches(BlockState state) {
        return matches(state.getBlock());
    }

    @Override
    public List<BlockState> getDisplayStates() {
        if(displayStates == null)
            displayStates = named.stream().map(h -> h.value().defaultBlockState()).toList();
        return displayStates;
    }

    @Override
    public MapCodec<? extends StateMatcher> typeCodec() {
        return CODEC;
    }

    public TagKey<Block> getTag() {
        return tag;
    }

}
