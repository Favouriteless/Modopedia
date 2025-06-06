package net.favouriteless.modopedia.platform.services;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class FabricClientPlatformHelper implements IClientPlatformHelper {

    @Override
    public Iterable<RenderType> getRenderTypes(BlockAndTintGetter level, BlockPos pos, BlockState state) {
        return List.of(ItemBlockRenderTypes.getChunkRenderType(state));
    }

}