package net.favouriteless.modopedia.common.network.packets.client;

import io.netty.buffer.ByteBuf;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.client.BookOpenHandler;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;

import java.util.Optional;

public record OpenCategoryPayload(Optional<ResourceLocation> book, String category) implements CustomPacketPayload {

	public static final Type<OpenCategoryPayload> TYPE = new Type<>(Modopedia.id("open_category"));

	public static final StreamCodec<ByteBuf, OpenCategoryPayload> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), OpenCategoryPayload::book,
			ByteBufCodecs.STRING_UTF8, OpenCategoryPayload::category,
			OpenCategoryPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public void handle() {
		book.ifPresentOrElse(id -> BookOpenHandler.tryOpenCategory(id, category), () -> BookOpenHandler.tryOpenCategory(category));
	}

}
