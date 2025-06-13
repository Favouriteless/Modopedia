package net.favouriteless.modopedia.common.network.packets.client;

import io.netty.buffer.ByteBuf;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.client.BookOpenHandler;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record OpenBookPayload(ResourceLocation book) implements CustomPacketPayload {

	public static final Type<OpenBookPayload> TYPE = new Type<>(Modopedia.id("open_book"));

	public static final StreamCodec<ByteBuf, OpenBookPayload> STREAM_CODEC = StreamCodec.composite(
			ResourceLocation.STREAM_CODEC, OpenBookPayload::book,
			OpenBookPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public void handle() {
		BookOpenHandler.tryOpenBook(book);
	}

}
