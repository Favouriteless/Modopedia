package net.favouriteless.modopedia.platform.services;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import com.mojang.blaze3d.vertex.*;
import net.neoforged.neoforge.client.model.data.ModelData;

public class NeoClientPlatformHelper implements IClientPlatformHelper {

	private static final RandomSource RAND = RandomSource.create();

	@Override
	public void renderBatched(BlockState state, BlockPos pos, BlockAndTintGetter level, PoseStack pose, MultiBufferSource bufferSource,
			boolean checkSides, boolean noOffsets) {
		BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();

		// RNG is seeded by the state (model variants and other such things)
		RAND.setSeed(state.getSeed(pos));

		ModelData modelData = ModelData.EMPTY;
		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity != null) {
			// BE model data first
			modelData = blockEntity.getModelData();
		}
		BakedModel model = dispatcher.getBlockModel(state);
		// The model might have model data
		modelData = model.getModelData(level, pos, state, modelData);

		for (RenderType type : model.getRenderTypes(state, RAND, modelData)) {
			VertexConsumer buffer = bufferSource.getBuffer(type);

			if (noOffsets) {
				Vec3 offset = state.getOffset(level, pos);
				pose.translate(-offset.x, -offset.y, -offset.z);
			}

			dispatcher.renderBatched(state, pos, level, pose, buffer, checkSides, RAND, modelData, type);
		}
	}
}