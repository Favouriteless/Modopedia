package net.favouriteless.modopedia.api.multiblock;

import net.favouriteless.modopedia.client.multiblock.render.MultiblockVisualiserImpl;
import net.favouriteless.modopedia.client.screens.ConfigureMultiblockScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * Creates and manages {@link MultiblockInstance}s for the client to render them.
 */
public interface MultiblockVisualiser {

    static MultiblockVisualiser get() {
        return MultiblockVisualiserImpl.INSTANCE;
    }

    /**
     * Place an instance of the given multiblock into a dimension and render it. If a multiblock of the same type
     * is already at the given position, it will be returned instead.
     *
     * @param multiblock The multiblock to be rendered.
     * @param dimension The dimension to render the multiblock in.
     * @param pos The position the instance should be placed at.
     *
     * @return The {@link MultiblockInstance} now being rendered.
     */
    MultiblockInstance place(Multiblock multiblock, ResourceKey<Level> dimension, BlockPos pos);

    default MultiblockInstance place(Multiblock multiblock, Level level, BlockPos pos) {
        return place(multiblock, level.dimension(), pos);
    }

    /**
     * Remove the given {@link MultiblockInstance} from the renderer.
     */
    void remove(MultiblockInstance multiblock);

    /**
     * @return The first {@link MultiblockInstance} the given player is looking at, or null if none were found.
     */
    @Nullable MultiblockInstance getSelected(Player player);

    /**
     * Attempts to open a {@link ConfigureMultiblockScreen} for the given {@link Player}. If the player is not looking
     * at any {@link MultiblockInstance}, no screen will be opened.
     *
     * @return {@code true} if a screen was opened, otherwise {@code false}.
     */
    boolean tryOpenConfigureScreen(Player player);

}
