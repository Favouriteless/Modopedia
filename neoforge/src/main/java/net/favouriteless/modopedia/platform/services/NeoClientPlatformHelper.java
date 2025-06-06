package net.favouriteless.modopedia.platform.services;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;

public class NeoClientPlatformHelper implements IClientPlatformHelper {

    private static final RandomSource RAND = RandomSource.create();

    @Override
    public Iterable<RenderType> getRenderTypes(BlockAndTintGetter level, BlockPos pos, BlockState state) {
        BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(state);
        return model.getRenderTypes(state, RAND, model.getModelData(level, pos, state, ModelData.EMPTY));
    }
}