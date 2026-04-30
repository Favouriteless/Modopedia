package net.favouriteless.modopedia.client.multiblock.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.favouriteless.modopedia.api.multiblock.Multiblock;
import net.favouriteless.modopedia.api.multiblock.MultiblockInstance;
import net.favouriteless.modopedia.api.multiblock.MultiblockVisualiser;
import net.favouriteless.modopedia.api.multiblock.StateMatcher;
import net.favouriteless.modopedia.client.multiblock.PlacedMultiblock;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.*;

public class MultiblockVisualiserImpl implements MultiblockVisualiser {

    public static final MultiblockVisualiserImpl INSTANCE = new MultiblockVisualiserImpl();

    private final Map<ResourceKey<Level>, Set<MultiblockInstance>> multiblocks = new HashMap<>();

    private MultiblockVisualiserImpl() {}

    @Override
    public MultiblockInstance place(Multiblock multiblock, ResourceKey<Level> dimension, BlockPos pos) {
        if(multiblocks.containsKey(dimension)) {
            for(MultiblockInstance instance : multiblocks.get(dimension)) {
                if(instance.getPos().equals(pos))
                    return instance;
            }
        }

        MultiblockInstance instance = new PlacedMultiblock(multiblock, dimension, pos);
        multiblocks.computeIfAbsent(dimension, k -> new HashSet<>()).add(instance);
        return instance;
    }

    @Override
    public void remove(MultiblockInstance multiblock) {
        ResourceKey<Level> dimension = multiblock.getDimension();
        if(!multiblocks.containsKey(dimension))
            return;
        multiblocks.get(dimension).remove(multiblock);
    }

    @Override
    public MultiblockInstance getSelected(Player player) {
        Level level = player.level();
        Set<MultiblockInstance> instances = multiblocks.get(level.dimension());
        if(instances == null || instances.isEmpty())
            return null;

        float delta = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
        Vec3 start = player.getEyePosition(delta);
        Vec3 end = start.add(player.getViewVector(delta).scale(25));

        for(MultiblockInstance instance : instances) {
            BlockPos pos = instance.getPos();
            Vec3i size = instance.getMultiblock().getDimensions();
            AABB aabb = new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + size.getX(), pos.getY() + size.getY(), pos.getZ() + size.getZ());

            if(aabb.intersects(start, end))
                return instance;
        }

        return null;
    }

    public void tick() {
        multiblocks.values().forEach(set -> set.forEach(MultiblockInstance::tick));
    }

    public void render(Level level, PoseStack pose, BufferSource bufferSource, float partialTicks) {
        if(!multiblocks.containsKey(level.dimension()))
            return;

        Minecraft mc = Minecraft.getInstance();
        Camera camera = mc.gameRenderer.getMainCamera();

        double cx = camera.getPosition().x();
        double cy = camera.getPosition().y();
        double cz = camera.getPosition().z();

        for(MultiblockInstance instance : multiblocks.get(level.dimension())) {
            Multiblock multiblock = instance.getMultiblock();
            BlockPos corner = instance.getPos();
            Vec3i dims = multiblock.getDimensions();

            pose.pushPose();
            pose.translate(corner.getX() - cx, corner.getY() - cy, corner.getZ() - cz);

            MultiblockRenderer.renderInLevel(level, instance, pose, bufferSource, partialTicks);

            VertexConsumer buffer = bufferSource.getBuffer(RenderType.lines());
            for(BlockPos local : BlockPos.betweenClosed(0, 0, 0, dims.getX()-1, dims.getY()-1, dims.getZ()-1)) {
                StateMatcher matcher = multiblock.getStateMatcher(local);
                if(matcher == null)
                    continue;

                BlockPos pos = corner.offset(local);
                BlockState state = level.getBlockState(pos);

                MatcherResult p = MatcherResult.PARTIAL_MATCH;
                MatcherResult result = matches(state, matcher);
                if(result == MatcherResult.FULL_MATCH)
                    continue;


                VoxelShape shape = !state.isAir() ? state.getShape(level, pos) : instance.getBlockState(local).getShape(level, local);
                if(shape.isEmpty())
                    return;

                pose.pushPose();

                if(state.isAir())
                    LevelRenderer.renderVoxelShape(pose, buffer, shape, local.getX(), local.getY(), local.getZ(), p.r, p.g, p.b, 1.0F, false);
                else
                    LevelRenderer.renderVoxelShape(pose, buffer, shape, local.getX(), local.getY(), local.getZ(), result.r, result.g, result.b, 1.0F, false);
                pose.popPose();
            }

            pose.popPose();
        }
        bufferSource.endBatch();
    }

    private MatcherResult matches(BlockState state, StateMatcher matcher) {
        if(!matcher.matches(state.getBlock()))
            return MatcherResult.NO_MATCH;
        if(matcher.matches(state))
            return MatcherResult.FULL_MATCH;
        return MatcherResult.PARTIAL_MATCH;
    }

    private enum MatcherResult {
        FULL_MATCH(0,0,0), // Skips rendering
        PARTIAL_MATCH(0.11F, 0.54F, 0.9F), // Blue #1E8BE5
        NO_MATCH(0.82F, 0.14F, 0.14F); // Red #D22424

        private final float r, g, b;

        MatcherResult(float r, float g, float b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

    }

}
