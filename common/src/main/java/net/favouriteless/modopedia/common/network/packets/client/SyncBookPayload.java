package net.favouriteless.modopedia.common.network.packets.client;

import io.netty.buffer.ByteBuf;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.registries.common.BookRegistry;
import net.favouriteless.modopedia.api.book.Book;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SyncBookPayload(ResourceLocation id, Book book) implements CustomPacketPayload {

	public static final Type<SyncBookPayload> TYPE = new Type<>(Modopedia.id("sync_book"));

	public static final StreamCodec<ByteBuf, SyncBookPayload> STREAM_CODEC = StreamCodec.composite(
			ResourceLocation.STREAM_CODEC, SyncBookPayload::id,
			Book.streamCodec(), SyncBookPayload::book,
			SyncBookPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public void handle() {
		BookRegistry.get().register(id, book);
	}

}
