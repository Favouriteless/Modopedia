package net.favouriteless.modopedia.api;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.books.components.TextPageComponent;

public class DefaultPageComponents {

    public static void init() {
        PageComponentRegistry registry = ModopediaApi.get().getComponentRegistry();
        registry.register(Modopedia.id("text"), TextPageComponent.CODEC);
    }

}
