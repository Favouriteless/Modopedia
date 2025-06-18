package net.favouriteless.modopedia.api.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;

/**
 * Represents an instance of a {@link Multiblock} in a level.
 */
public interface MultiblockInstance extends BlockAndTintGetter {

    Multiblock getMultiblock();

    Level getLevel();

    BlockPos getPos();

    void setPos(BlockPos pos);

    void tick();

}
