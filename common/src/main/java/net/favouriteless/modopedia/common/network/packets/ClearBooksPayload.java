package net.favouriteless.modopedia.common.network.packets;

import io.netty.buffer.ByteBuf;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.common.BookRegistry;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class ClearBooksPayload implements CustomPacketPayload {

	public static final Type<ClearBooksPayload> TYPE = new Type<>(Modopedia.id("clear_books"));

	public static final StreamCodec<ByteBuf, ClearBooksPayload> STREAM_CODEC = StreamCodec.unit(new ClearBooksPayload());

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public void handle() {
		BookRegistry.clear();
	}

}
