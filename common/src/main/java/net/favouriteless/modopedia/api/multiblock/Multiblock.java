package net.favouriteless.modopedia.api.multiblock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.favouriteless.modopedia.api.registries.MultiblockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import org.jetbrains.annotations.Nullable;

/**
 * Defines a group of {@link StateMatcher}s within a bounding box. Does not define rotations, offsets or any other
 * per-instance data.
 */
public interface Multiblock {

    /**
     * @return The main {@link Multiblock} dispatch codec.
     */
    static Codec<Multiblock> codec() {
        return MultiblockRegistry.get().codec();
    }

    /**
     * @return The size of this multiblock's bounding box.
     */
    Vec3i getDimensions();

    /**
     * @return The {@link StateMatcher} present at pos, or null if there is no matcher.
     */
    @Nullable StateMatcher getStateMatcher(BlockPos pos);

    /**
     * @return {@link MapCodec} for this type of Multiblock.
     */
    MapCodec<? extends Multiblock> typeCodec();

}
