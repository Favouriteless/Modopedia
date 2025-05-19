package net.favouriteless.modopedia.multiblock;

import net.favouriteless.modopedia.util.PosUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class SimpleMultiblock extends AbstractMultiblock {

    protected BlockState[] states;
    protected Vec3i dimensions;

    public SimpleMultiblock(String[][] pattern, Map<Character, BlockState> key) {
        int xSize = pattern[0][0].length();
        int zSize = pattern[0].length;

        dimensions = new Vec3i(xSize, pattern.length, zSize);
        states = new BlockState[xSize * pattern.length * zSize];

        for(int y = 0; y < pattern.length; y++) {
            String[] layer = pattern[y];

            if(layer.length != zSize)
                throw new IllegalArgumentException("SimpleMultiblocks must have a rectangle footprint.");

            for(int z = 0; z < layer.length; z++) {
                String row = layer[z];

                if(row.length() != xSize)
                    throw new IllegalArgumentException("SimpleMultiblocks must have a rectangle footprint.");

                for(int x = 0; x < row.length(); x++)
                    states[PosUtils.posToIndex(x, y, z, xSize, zSize)] = key.get(row.charAt(x));
            }
        }

        dimensions = new Vec3i(xSize, pattern.length, zSize);
    }


    @Override
    public BlockState getBlockState(BlockPos pos) {
        return states[PosUtils.posToIndex(pos, dimensions)];
    }
    
}
