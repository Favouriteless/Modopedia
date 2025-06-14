package net.favouriteless.modopedia.client;

import net.favouriteless.modopedia.client.init.MBookScreenFactories;
import net.favouriteless.modopedia.client.init.MPageComponents;
import net.favouriteless.modopedia.client.init.MTextFormatters;

public class ModopediaClient {

    public static void init() {
        MTextFormatters.load();
        MPageComponents.load();
        MBookScreenFactories.load();
    }

}
