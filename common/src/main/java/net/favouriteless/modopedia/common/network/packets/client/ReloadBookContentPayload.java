package net.favouriteless.modopedia.common.network.packets.client;

import io.netty.buffer.ByteBuf;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.book.loading.BookContentLoader;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class ReloadBookContentPayload implements CustomPacketPayload {

	public static final Type<ReloadBookContentPayload> TYPE = new Type<>(Modopedia.id("reload_book_content"));

	public static final StreamCodec<ByteBuf, ReloadBookContentPayload> STREAM_CODEC = StreamCodec.of((b, p) -> {}, b -> new ReloadBookContentPayload());

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public void handle() {
		BookContentLoader.reloadAll();
	}

}
