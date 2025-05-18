package net.favouriteless.modopedia.common.network.packets.client;

import io.netty.buffer.ByteBuf;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.registries.BookRegistry;
import net.favouriteless.modopedia.common.BookRegistryImpl;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class ClearBooksPayload implements CustomPacketPayload {

	public static final Type<ClearBooksPayload> TYPE = new Type<>(Modopedia.id("clear_books"));

	// Not a unit stream codec because it's a different instance of the payload each time.
	public static final StreamCodec<ByteBuf, ClearBooksPayload> STREAM_CODEC = StreamCodec.of((b, p) -> {}, b -> new ClearBooksPayload());

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public void handle() {
		((BookRegistryImpl)BookRegistry.get()).clear();
	}

}
