package net.favouriteless.modopedia.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.favouriteless.modopedia.client.ModopediaClient;

public class ModopediaFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModopediaClient.init();
    }

}
