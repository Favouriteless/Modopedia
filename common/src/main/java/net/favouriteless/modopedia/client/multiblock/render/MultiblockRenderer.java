package net.favouriteless.modopedia.client.multiblock.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.favouriteless.modopedia.api.multiblock.MultiblockInstance;
import net.favouriteless.modopedia.platform.ClientServices;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MultiblockRenderer {

    public static void render(MultiblockInstance multiblock, PoseStack pose, MultiBufferSource bufferSource, float partialTicks) {
        renderBlocks(multiblock, pose, bufferSource, multiblock, false);
        renderBlockEntities(multiblock, pose, bufferSource, partialTicks, multiblock, false);
    }

    public static void render(Level level, MultiblockInstance multiblock, PoseStack pose, MultiBufferSource bufferSource, float partialTicks) {
        renderBlocks(multiblock, pose, bufferSource, level, true);
        renderBlockEntities(multiblock, pose, bufferSource, partialTicks, level, true);
    }

    protected static void renderBlocks(MultiblockInstance multiblock, PoseStack pose, MultiBufferSource bufferSource,
                                       BlockGetter level, boolean checkState) {
        Vec3i dims = multiblock.getMultiblock().getDimensions();
        for(BlockPos pos : BlockPos.betweenClosed(0, 0, 0, dims.getX()-1, dims.getY()-1, dims.getZ()-1)) {
            pose.pushPose();
            pose.translate(pos.getX(), pos.getY(), pos.getZ());

            BlockState state = multiblock.getBlockState(pos);
            boolean shouldRender = !checkState || level.getBlockState(multiblock.getPos().offset(pos)).isAir();
            if(shouldRender && state.getRenderShape() == RenderShape.MODEL)
                ClientServices.PLATFORM.renderBatched(state, pos, multiblock, pose, bufferSource, false, false);

            pose.popPose();
        }
    }

    protected static void renderBlockEntities(MultiblockInstance multiblock, PoseStack pose, MultiBufferSource bufferSource,
                                              float partialTicks, BlockGetter level, boolean checkState) {
        Minecraft mc = Minecraft.getInstance();
        Vec3i dims = multiblock.getMultiblock().getDimensions();

        for(BlockPos pos : BlockPos.betweenClosed(0, 0, 0, dims.getX(), dims.getY(), dims.getZ())) {
            BlockEntity be = multiblock.getBlockEntity(pos);
            if(be == null)
                continue;

            be.setLevel(mc.level);

            pose.pushPose();
            pose.translate(pos.getX(), pos.getY(), pos.getZ());

            if(!checkState || level.getBlockState(multiblock.getPos().offset(pos)).isAir()) {
                try {
                    BlockEntityRenderer<BlockEntity> renderer = mc.getBlockEntityRenderDispatcher().getRenderer(be);
                    if(renderer != null)
                        renderer.render(be, partialTicks, pose, bufferSource, 0xF000F0, OverlayTexture.NO_OVERLAY);
                }
                catch(Exception ignored) {}
            }

            pose.popPose();
        }
    }

}
