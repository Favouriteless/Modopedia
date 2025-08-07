package net.favouriteless.modopedia.platform.services;

import com.mojang.blaze3d.vertex.*;

import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

public interface IClientPlatformHelper {

	/**
	 * Renders a batched block model via either A.) {@link BlockRenderDispatcher#renderBatched} (fabric) or B.) BE ModelData followed by {@link BlockRenderDispatcher#renderBatched} (NeoForge).
	 *
	 * @param noOffsets If true, negate any block offsets (e.g. flowers)
	 */
	void renderBatched(BlockState state, BlockPos pos, BlockAndTintGetter level, PoseStack pose, MultiBufferSource bufferSource,
					   boolean checkSides, boolean noOffsets);
}