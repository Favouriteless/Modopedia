package net.favouriteless.modopedia.common.data_components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public record BookDataComponent(@Nonnull ResourceLocation id) {

    public static final Codec<BookDataComponent> PERSISTENT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(BookDataComponent::id)
            ).apply(instance, BookDataComponent::new)
    );

    public static final StreamCodec<ByteBuf, BookDataComponent> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, BookDataComponent::id, BookDataComponent::new
    );

}
