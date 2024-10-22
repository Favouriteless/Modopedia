package net.favouriteless.modopedia.common.network.packets;

import io.netty.buffer.ByteBuf;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.BookRegistry;
import net.favouriteless.modopedia.api.books.Book;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record SyncBookPayload(Book book) implements CustomPacketPayload {

	public static final Type<SyncBookPayload> TYPE = new Type<>(Modopedia.id("sync_book"));

	public static final StreamCodec<ByteBuf, SyncBookPayload> STREAM_CODEC = StreamCodec.composite(
			Book.streamCodec(), SyncBookPayload::book,
			SyncBookPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public void handle() {
		BookRegistry.get().register(book.getId(), book);
	}

}
