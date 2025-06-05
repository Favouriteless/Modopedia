package net.favouriteless.modopedia.multiblock;

import net.favouriteless.modopedia.api.multiblock.Multiblock;
import net.favouriteless.modopedia.api.multiblock.MultiblockInstance;
import net.favouriteless.modopedia.api.multiblock.StateMatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlacedMultiblock implements MultiblockInstance {

    public static final int TICKS_PER_STATE = 20;

    private final Multiblock multiblock;

    private final Level level;
    public BlockPos pos;
    private int ticks = 0;

    private final Map<BlockPos, BlockEntity> blockEntities = new HashMap<>();

    public PlacedMultiblock(Multiblock multiblock, Level level, BlockPos pos) {
        this.multiblock = multiblock;
        this.level = level;
        this.pos = pos;
    }

    public PlacedMultiblock(Multiblock multiblock, Level level) {
        this(multiblock, level, BlockPos.ZERO);
    }

    @Override
    public float getShade(Direction direction, boolean shade) {
        return 1.0F;
    }

    @Override
    public LevelLightEngine getLightEngine() {
        return null;
    }

    @Override
    public int getBlockTint(BlockPos pos, ColorResolver resolver) {
        Biome biome = level.registryAccess().registryOrThrow(Registries.BIOME).getOrThrow(Biomes.FOREST);
        return resolver.getColor(biome, pos.getX(), pos.getY());
    }

    @Override
    public BlockEntity getBlockEntity(BlockPos pos) {
        BlockState state = getBlockState(pos);

        if(state.getBlock() instanceof EntityBlock block) {
            if(blockEntities.containsKey(pos) && !blockEntities.get(pos).getBlockState().equals(state))
                blockEntities.remove(pos);

            return blockEntities.computeIfAbsent(pos, k -> block.newBlockEntity(pos, state));
        }

        return null;
    }

    @Override
    public BlockState getBlockState(BlockPos pos) {
        Vec3i dim = multiblock.getDimensions();
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if (x < 0 || y < 0 || z < 0 || x >= dim.getX() || y >= dim.getY() || z >= dim.getZ())
            return Blocks.AIR.defaultBlockState();

        StateMatcher matcher = multiblock.getStateMatcher(pos);

        if(matcher == null)
            return Blocks.AIR.defaultBlockState();

        List<BlockState> states = matcher.getDisplayStates();

        return states.get((ticks / TICKS_PER_STATE) % states.size());
    }

    @Override
    public FluidState getFluidState(BlockPos pos) {
        return Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public int getHeight() {
        return level.getMaxBuildHeight();
    }

    @Override
    public int getMinBuildHeight() {
        return level.getMinBuildHeight();
    }

    @Override
    public int getBrightness(LightLayer layer, BlockPos pos) {
        return 15;
    }

    @Override
    public int getRawBrightness(BlockPos pos, int amount) {
        return 15 - amount;
    }

    public Multiblock getMultiblock() {
        return multiblock;
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public BlockPos getPos() {
        return pos;
    }

    @Override
    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public void tick() {
        ticks++;
    }

}
