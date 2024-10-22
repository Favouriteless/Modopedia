package net.favouriteless.modopedia.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.common.network.packets.ClearBooksPayload;
import net.favouriteless.modopedia.common.network.packets.SyncBookPayload;
import net.favouriteless.modopedia.fabric.common.FabricCommonEvents;

public class ModopediaFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Modopedia.init();
        registerPackets();
        FabricCommonEvents.register();
    }

    public void registerPackets() {
        PayloadTypeRegistry.playS2C().register(ClearBooksPayload.TYPE, ClearBooksPayload.STREAM_CODEC);
        ClientPlayNetworking.registerGlobalReceiver(ClearBooksPayload.TYPE, (payload, context) -> payload.handle());

        PayloadTypeRegistry.playS2C().register(SyncBookPayload.TYPE, SyncBookPayload.STREAM_CODEC);
        ClientPlayNetworking.registerGlobalReceiver(SyncBookPayload.TYPE, (payload, context) -> payload.handle());
    }

}
