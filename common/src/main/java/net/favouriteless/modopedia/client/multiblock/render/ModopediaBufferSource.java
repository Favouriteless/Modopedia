package net.favouriteless.modopedia.client.multiblock.render;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

import java.util.SequencedMap;

public class ModopediaBufferSource extends MultiBufferSource.BufferSource {

    public ModopediaBufferSource(ByteBufferBuilder sharedBuffer, SequencedMap<RenderType, ByteBufferBuilder> fixedBuffers) {
        super(sharedBuffer, fixedBuffers);
    }

    @Override
    public VertexConsumer getBuffer(RenderType type) {
        return super.getBuffer(ModopediaRenderType.get(type));
    }

}