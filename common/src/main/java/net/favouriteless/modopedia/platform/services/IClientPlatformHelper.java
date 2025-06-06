package net.favouriteless.modopedia.platform.services;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

public interface IClientPlatformHelper {

    /**
     * @return Iterable for every RenderType present in a block's model. On NeoForge, calls NeoForge's
     * IBakedModelExtension patch. On fabric, just wraps the vanilla method in a list. Necessary because Neo's patch
     * allows multiple RenderTypes per model.
     */
    Iterable<RenderType> getRenderTypes(BlockAndTintGetter level, BlockPos pos, BlockState state);

}