package net.favouriteless.modopedia.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMultiblock implements BlockAndTintGetter {

    private final Map<BlockPos, BlockEntity> blockEntities = new HashMap<>();
    protected Level level = null;

    @Override
    public float getShade(Direction direction, boolean shade) {
        return 1.0F;
    }

    @Override
    public LevelLightEngine getLightEngine() {
        return level.getLightEngine();
    }

    @Override
    public int getBlockTint(BlockPos pos, ColorResolver resolver) {
        Biome biome = level.registryAccess().registryOrThrow(Registries.BIOME).getOrThrow(Biomes.FOREST);
        return resolver.getColor(biome, pos.getX(), pos.getY());
    }

    @Override
    public @Nullable BlockEntity getBlockEntity(BlockPos pos) {
        BlockState state = getBlockState(pos);
        if(state.getBlock() instanceof EntityBlock block) {
            return blockEntities.computeIfAbsent(pos, k -> block.newBlockEntity(pos, state));
        }
        return null;
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
    public int getBrightness(LightLayer lightType, BlockPos pos) {
        return 15;
    }

    @Override
    public int getRawBrightness(BlockPos pos, int amount) {
        return 15 - amount;
    }

    @Override
    public FluidState getFluidState(BlockPos pos) {
        return Fluids.EMPTY.defaultFluidState();
    }

}
