package net.favouriteless.modopedia.book.page_components;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.favouriteless.modopedia.api.Lookup;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.api.multiblock.Multiblock;
import net.favouriteless.modopedia.api.multiblock.MultiblockInstance;
import net.favouriteless.modopedia.api.registries.MultiblockRegistry;
import net.favouriteless.modopedia.multiblock.PlacedMultiblock;
import net.favouriteless.modopedia.platform.ClientServices;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class MultiblockPageComponent extends PageComponent {

    private static final RandomSource RANDOM = RandomSource.createNewThreadLocalInstance();

    private MultiblockInstance multiblock;
    private int width;
    private int height;

    private float offsetX;
    private float offsetY;
    private float scale;

    private boolean noOffsets;

    @Override
    public void init(Book book, Lookup lookup, Level level) {
        super.init(book, lookup, level);

        Multiblock multiblock = null;

        if(lookup.has("multiblock_id")) {
            ResourceLocation id = lookup.get("multiblock_id").as(ResourceLocation.class);
            multiblock = MultiblockRegistry.get().get(id);

            if(multiblock == null)
                throw new IllegalArgumentException(id + " is not a registered multiblock");
        }


        if(multiblock == null && lookup.has("multiblock"))
            multiblock = lookup.get("multiblock").as(Multiblock.class);

        if(multiblock == null)
            throw new NullPointerException("Lookup is missing a \"multiblock_id\" or \"multiblock\" key");

        this.multiblock = new PlacedMultiblock(multiblock, level);
        width = lookup.getOrDefault("width", 100).asInt();
        height = lookup.getOrDefault("height", 100).asInt();

        offsetX = lookup.getOrDefault("offset_x", 0.0F).asFloat();
        offsetY = lookup.getOrDefault("offset_y", 0.0F).asFloat();
        scale = lookup.getOrDefault("scale", 1.0F).asFloat();
        noOffsets = lookup.getOrDefault("no_offsets", false).asBoolean();
    }

    @Override
    public void tick(BookRenderContext context) {
        multiblock.tick();
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick) {
        Minecraft mc = Minecraft.getInstance();
        MultiBufferSource bufferSource = mc.renderBuffers().bufferSource();

        if(multiblock.getLevel() != mc.level)
            multiblock = new PlacedMultiblock(multiblock.getMultiblock(), mc.level, multiblock.getPos());

        PoseStack pose = graphics.pose();

        Multiblock m = multiblock.getMultiblock();
        Vec3i dims = m.getDimensions();

        float scale = -Math.min(width, height) / Mth.sqrt(dims.getX() * dims.getX() + dims.getY() * dims.getY() + dims.getZ() * dims.getZ());

        pose.pushPose();
        pose.translate(offsetX, offsetY, 0);

        pose.translate(x + width / 2.0F, y + height / 2.0F, 100);
        pose.scale(scale, scale, scale);
        pose.translate(-dims.getX() / 2.0F, -dims.getY() / 2.0F, 0);

        pose.mulPose(Axis.XN.rotationDegrees(30));
        
        pose.translate(dims.getX() / 2.0F, 0, dims.getZ() / 2.0F);
        pose.mulPose(Axis.YP.rotationDegrees(context.getTicks() + partialTick));
        pose.translate(-dims.getX() / 2.0F, 0, -dims.getZ() / 2.0F);

        pose.scale(this.scale, this.scale, this.scale);

        renderBlocks(pose, bufferSource, dims, partialTick);
        renderBlockEntities(pose, bufferSource, dims, partialTick);

        pose.popPose();
    }

    protected void renderBlocks(PoseStack pose, MultiBufferSource bufferSource, Vec3i dims, float partialTicks) {
        BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
        for(BlockPos pos : BlockPos.betweenClosed(0, 0, 0, dims.getX(), dims.getY(), dims.getZ())) {
            pose.pushPose();
            pose.translate(pos.getX(), pos.getY(), pos.getZ());

            BlockState state = multiblock.getBlockState(pos);
            if(state.getRenderShape() == RenderShape.MODEL) {
                for(RenderType type : ClientServices.PLATFORM.getRenderTypes(multiblock, pos, state)) {
                    VertexConsumer buffer = bufferSource.getBuffer(type);

                    if(noOffsets) {
                        Vec3 offset = state.getOffset(multiblock, pos);
                        pose.translate(-offset.x, -offset.y, -offset.z);
                    }

                    dispatcher.renderBatched(state, pos, multiblock, pose, buffer, false, RANDOM);
                }
            }

            pose.popPose();
        }
    }

    protected void renderBlockEntities(PoseStack pose, MultiBufferSource bufferSource, Vec3i dims, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();

        for(BlockPos pos : BlockPos.betweenClosed(0, 0, 0, dims.getX(), dims.getY(), dims.getZ())) {
            BlockEntity be = multiblock.getBlockEntity(pos);

            if(be == null)
                continue;

            be.setLevel(mc.level);

            pose.pushPose();
            pose.translate(pos.getX(), pos.getY(), pos.getZ());

            try {
                BlockEntityRenderer<BlockEntity> renderer = mc.getBlockEntityRenderDispatcher().getRenderer(be);
                if(renderer != null)
                    renderer.render(be, partialTicks, pose, bufferSource, 0xF000F0, OverlayTexture.NO_OVERLAY);
            }
            catch(Exception ignored) {

            }

            pose.popPose();
        }
    }

}
