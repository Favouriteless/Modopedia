package net.favouriteless.modopedia.client.init;

import net.favouriteless.modopedia.api.registries.client.BookScreenFactoryRegistry;
import net.favouriteless.modopedia.client.screen_factories.ClassicScreenFactory;
import net.favouriteless.modopedia.client.screen_factories.PageScreenFactory;
import net.favouriteless.modopedia.client.screen_factories.PamphletScreenFactory;
import net.favouriteless.modopedia.common.init.MBookTypes;

public class MBookScreenFactories {

    public static void load() {
        BookScreenFactoryRegistry registry = BookScreenFactoryRegistry.get();

        registry.register(MBookTypes.CLASSIC, new ClassicScreenFactory());
        registry.register(MBookTypes.PAMPHLET, new PamphletScreenFactory());
        registry.register(MBookTypes.PAGE, new PageScreenFactory());
    }

}
