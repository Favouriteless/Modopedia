package net.favouriteless.modopedia.client.multiblock;

import net.favouriteless.modopedia.api.multiblock.*;

import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.*;

import java.util.*;

public class PlacedMultiblock implements MultiblockInstance {

    public static final int TICKS_PER_STATE = 20;

    private final Multiblock multiblock;

    private final Level level;
    public BlockPos pos;
    private int ticks = 0;

    private final Map<BlockPos, BlockEntity> beCache = new HashMap<>();

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
            if(beCache.containsKey(pos) && !beCache.get(pos).getType().isValid(state))
                beCache.remove(pos);

            BlockEntity be = beCache.computeIfAbsent(pos, k -> {
                BlockEntity _be = block.newBlockEntity(pos, state);
                if(multiblock.getStateMatcher(pos) instanceof BEStateMatcher<?> beMatcher)
                    beMatcher.init(_be);

                return _be;
            });

            if(be != null)
                be.setBlockState(state);

            return be;
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

        if(states.isEmpty())
            return Blocks.AIR.defaultBlockState();

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
