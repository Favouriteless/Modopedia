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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MultiblockVisualiserImpl implements MultiblockVisualiser {

    public static final MultiblockVisualiserImpl INSTANCE = new MultiblockVisualiserImpl();

    private final Map<ResourceKey<Level>, Set<MultiblockInstance>> multiblocks = new HashMap<>();

    private MultiblockVisualiserImpl() {}

    @Override
    public MultiblockInstance place(Multiblock multiblock, Level level, BlockPos pos) {
        remove(level, multiblock);
        MultiblockInstance instance = new PlacedMultiblock(multiblock, level, pos);
        multiblocks.computeIfAbsent(level.dimension(), k -> new HashSet<>()).add(instance);
        return instance;
    }

    @Override
    public void remove(Level level, Multiblock multiblock) {
        if(!multiblocks.containsKey(level.dimension()))
            return;
        multiblocks.get(level.dimension()).removeIf(i -> i.getMultiblock().equals(multiblock));
    }

    @Override
    public Collection<MultiblockInstance> getMultiblocksAt(Level level, BlockPos pos) {
        ResourceKey<Level> dim = level.dimension();
        return multiblocks.containsKey(dim) ? multiblocks.get(dim).stream().filter(i -> i.getPos().equals(pos)).toList() : Collections.emptySet();
    }

    @Override
    public @Nullable MultiblockInstance getIntersecting(Level level, Vec3 ray) {
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

            MultiblockRenderer.render(level, instance, pose, bufferSource, partialTicks);

            VertexConsumer buffer = bufferSource.getBuffer(RenderType.lines());
            for(BlockPos local : BlockPos.betweenClosed(0, 0, 0, dims.getX()-1, dims.getY()-1, dims.getZ()-1)) {
                StateMatcher matcher = multiblock.getStateMatcher(local);
                if(matcher == null)
                    continue;

                BlockPos pos = corner.offset(local);
                BlockState state = level.getBlockState(pos);

                MatcherResult result = matches(state, matcher);
                if(result == MatcherResult.FULL_MATCH)
                    continue;

                VoxelShape shape = !state.isAir() ? state.getShape(level, pos) : instance.getBlockState(local).getShape(level, local);
                if(shape.isEmpty())
                    return;

                pose.pushPose();
                if(state.isAir())
                    LevelRenderer.renderVoxelShape(pose, buffer, shape, local.getX(), local.getY(), local.getZ(), 0.11F, 0.54F, 0.9F, 1.0F, false);
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
