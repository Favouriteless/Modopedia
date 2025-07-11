package net.favouriteless.modopedia.client.multiblock.state_matchers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.multiblock.StateMatcher;
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
    private List<BlockState> displayStates;

    public TagStateMatcher(TagKey<Block> tag) {
        this.tag = tag;
    }

    @Override
    public boolean matches(BlockState state) {
        return BuiltInRegistries.BLOCK.getTag(tag)
                .map(holders -> holders.contains(state.getBlockHolder()))
                .orElse(false);
    }

    @Override
    public List<BlockState> getDisplayStates() {
        if(displayStates == null)
            displayStates = BuiltInRegistries.BLOCK.getTag(tag)
                    .map(t -> t.stream()
                            .map(h -> h.value().defaultBlockState()).toList()
                    ).orElse(List.of());
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
