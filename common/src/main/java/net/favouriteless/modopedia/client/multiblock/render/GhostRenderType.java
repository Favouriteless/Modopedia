package net.favouriteless.modopedia.client.multiblock.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.favouriteless.modopedia.Modopedia;
import net.minecraft.client.renderer.RenderType;

import java.util.IdentityHashMap;
import java.util.Map;

public class GhostRenderType extends RenderType {

    private static final Map<RenderType, GhostRenderType> types = new IdentityHashMap<>();

    private GhostRenderType(RenderType type) {
        super(String.format("%s_%s", Modopedia.MOD_ID, type.toString()), type.format(), type.mode(), type.bufferSize(), type.affectsCrumbling(), type.sortOnUpload(),
                () -> {
                    type.setupRenderState();
                    RenderSystem.enableBlend();

                    float[] colour = RenderSystem.getShaderColor();
                    RenderSystem.setShaderColor(colour[0], colour[1], colour[2], colour[3] * 0.4F);
                    },
                () -> {
                    RenderSystem.setShaderColor(1, 1, 1, 1);
                    RenderSystem.disableBlend();
                    type.clearRenderState();
                }
        );
    }

    public static RenderType get(RenderType type) {
        return types.computeIfAbsent(type, GhostRenderType::new);
    }

}