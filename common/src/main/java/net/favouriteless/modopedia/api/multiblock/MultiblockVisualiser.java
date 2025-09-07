package net.favouriteless.modopedia.api.multiblock;

import net.favouriteless.modopedia.client.multiblock.render.MultiblockVisualiserImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface MultiblockVisualiser {

    static MultiblockVisualiser get() {
        return MultiblockVisualiserImpl.INSTANCE;
    }

    MultiblockInstance place(Multiblock multiblock, Level level, BlockPos pos);

    void remove(Level level, Multiblock multiblock);

    Collection<MultiblockInstance> getMultiblocksAt(Level level, BlockPos pos);

    @Nullable MultiblockInstance getIntersecting(Level level, Vec3 ray);

}
