package net.favouriteless.modopedia;

import net.fabricmc.api.ModInitializer;

public class ModopediaFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Modopedia.init();
    }

}
