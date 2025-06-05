package net.favouriteless.modopedia.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;

public class PosUtils {

    /**
     * Converts an XYZ coordinate to an index for a flat array.
     */
    public static int posToIndex(BlockPos pos, Vec3i size) {
        return posToIndex(pos.getX(), pos.getY(), pos.getZ(), size.getX(), size.getY());
    }

    /**
     * Converts an XYZ coordinate to an index for a flat array.
     */
    public static int posToIndex(int x, int y, int z, int xSize, int ySize) {
        return x + (y * xSize) + (z * xSize * ySize);
    }

}
