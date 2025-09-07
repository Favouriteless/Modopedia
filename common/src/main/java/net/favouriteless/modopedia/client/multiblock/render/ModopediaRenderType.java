package net.favouriteless.modopedia.client.multiblock.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.favouriteless.modopedia.Modopedia;
import net.minecraft.client.renderer.RenderType;

import java.util.IdentityHashMap;
import java.util.Map;

public class ModopediaRenderType extends RenderType {

    private static final Map<RenderType, ModopediaRenderType> types = new IdentityHashMap<>();

    private ModopediaRenderType(RenderType type) {
        super(String.format("%s_%s", Modopedia.MOD_ID, type.toString()), type.format(), type.mode(), type.bufferSize(), type.affectsCrumbling(), true,
                () -> {
                    type.setupRenderState();
                    RenderSystem.disableDepthTest();
                    RenderSystem.enableBlend();
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.4F);
                    },
                () -> {
                    RenderSystem.setShaderColor(1, 1, 1, 1);
                    RenderSystem.disableBlend();
                    RenderSystem.enableDepthTest();
                    type.clearRenderState();
                }
        );
    }

    public static RenderType get(RenderType type) {
        return types.computeIfAbsent(type, ModopediaRenderType::new);
    }

}