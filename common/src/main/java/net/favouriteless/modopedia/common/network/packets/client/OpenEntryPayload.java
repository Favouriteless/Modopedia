package net.favouriteless.modopedia.common.network.packets.client;

import io.netty.buffer.ByteBuf;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.client.BookOpenHandler;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record OpenEntryPayload(ResourceLocation book, String entry) implements CustomPacketPayload {

	public static final Type<OpenEntryPayload> TYPE = new Type<>(Modopedia.id("open_entry"));

	public static final StreamCodec<ByteBuf, OpenEntryPayload> STREAM_CODEC = StreamCodec.composite(
			ResourceLocation.STREAM_CODEC, OpenEntryPayload::book,
			ByteBufCodecs.STRING_UTF8, OpenEntryPayload::entry,
			OpenEntryPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public void handle() {
		BookOpenHandler.tryOpenEntry(book, entry);
	}

}
