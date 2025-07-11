package net.favouriteless.modopedia.client;

import net.favouriteless.modopedia.client.init.*;

public class ModopediaClient {

    public static void init() {
        MTextFormatters.load();
        MPageComponents.load();
        MTemplateProcessors.load();
        MBookScreenFactories.load();
        MStateMatchers.load();
        MItemDisplays.load();
    }

}
