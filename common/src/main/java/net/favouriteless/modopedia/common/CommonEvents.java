package net.favouriteless.modopedia.common;

import net.favouriteless.modopedia.api.registries.common.BookRegistry;
import net.favouriteless.modopedia.common.network.packets.client.ClearBooksPayload;
import net.favouriteless.modopedia.common.network.packets.client.SyncBookPayload;
import net.favouriteless.modopedia.platform.CommonServices;
import net.favouriteless.modopedia.platform.services.INetworkHelper;
import net.minecraft.server.level.ServerPlayer;

public class CommonEvents {

    public static void datapackSync(ServerPlayer player) {
        INetworkHelper networkHelper = CommonServices.NETWORK;

        networkHelper.sendToPlayer(new ClearBooksPayload(), player); // When reloading clear the client's book registry and sync new ones; contents will sync automatically
        BookRegistry.get().getBooks().forEach(book -> networkHelper.sendToPlayer(new SyncBookPayload(BookRegistry.get().getId(book), book), player));
    }

}
