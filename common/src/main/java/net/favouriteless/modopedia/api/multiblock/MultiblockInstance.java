package net.favouriteless.modopedia.api.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;

/**
 * Represents an instance of a {@link Multiblock} in a level.
 */
public interface MultiblockInstance extends BlockAndTintGetter {

    /**
     * @return The {@link Multiblock} this instance is backed by.
     */
    Multiblock getMultiblock();

    /**
     * @return The key of the dimension this instance is in.
     */
    ResourceKey<Level> getDimension();

    /**
     * @return The position of the origin of this instance (the smallest corner).
     */
    BlockPos getPos();

    /**
     * Sets the instance's origin.
     */
    void setPos(BlockPos pos);

    void tick();

    /**
     * Moves the instance's origin along an axis.
     *
     * @param axis {@link Axis} to move on.
     * @param distance blocks to move by.
     */
    void move(Axis axis, int distance);

}
