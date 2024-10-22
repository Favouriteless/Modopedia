package net.favouriteless.modopedia.common;

import net.favouriteless.modopedia.common.network.packets.ClearBooksPayload;
import net.favouriteless.modopedia.common.network.packets.SyncBookPayload;
import net.favouriteless.modopedia.platform.CommonServices;
import net.favouriteless.modopedia.platform.services.INetworkHelper;
import net.minecraft.server.level.ServerPlayer;

public class CommonEvents {

    public static void datapackSync(ServerPlayer player) {
        INetworkHelper networkHelper = CommonServices.NETWORK;

        networkHelper.sendToPlayer(new ClearBooksPayload(), player);
        BookRegistry.getBooks().forEach(book -> networkHelper.sendToPlayer(new SyncBookPayload(book), player));
    }

}
