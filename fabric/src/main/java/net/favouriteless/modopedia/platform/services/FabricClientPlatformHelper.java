package net.favouriteless.modopedia.platform.services;

import com.mojang.blaze3d.vertex.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class FabricClientPlatformHelper implements IClientPlatformHelper {

    private static final RandomSource RAND = RandomSource.create();

    @Override
    public void renderBatched(BlockState state, BlockPos pos, BlockAndTintGetter level, PoseStack pose, MultiBufferSource bufferSource,
            boolean checkSides, boolean noOffsets) {
        BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();

	    // RNG is seeded by the state (model variants and other such things)
        RAND.setSeed(state.getSeed(pos));

        if (noOffsets) {
            Vec3 offset = state.getOffset(level, pos);
            pose.translate(-offset.x, -offset.y, -offset.z);
        }

        VertexConsumer buffer = bufferSource.getBuffer(ItemBlockRenderTypes.getChunkRenderType(state));

        dispatcher.renderBatched(state, pos, level, pose, buffer, false, RAND);
    }
}