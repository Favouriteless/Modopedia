package net.favouriteless.modopedia.platform.services;

import com.mojang.blaze3d.vertex.*;

import net.minecraft.client.renderer.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

public interface IClientPlatformHelper {

	/**
	 * Renders a batched block model. On NeoForge goes through the patched {@link net.minecraft.client.renderer.block.BlockRenderDispatcher#renderBatched BlockRenderDispatcher#renderBatched}
	 * handling Neos ModelData as well as the additional RenderType arg. On Fabric just goes through the vanilla method like normal.
	 * <p>
	 * Needed to handle cases like Tinkers dynamically textured baked models
	 * </p>
	 * @param noOffsets If we should negate offsets for blocks like flowers
	 */
	void renderBatched(BlockState state, BlockPos pos, BlockAndTintGetter level, PoseStack pose, MultiBufferSource bufferSource, boolean checkSides,
			boolean noOffsets);
}