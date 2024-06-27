package net.favouriteless.modopedia.common;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.ModopediaApi;
import net.favouriteless.modopedia.api.PageComponentRegistry;
import net.favouriteless.modopedia.books.components.TextPageComponent;

public class DefaultPageComponents {

    public static void init() {
        PageComponentRegistry registry = ModopediaApi.get().getComponentRegistry();
        registry.register(Modopedia.id("text"), TextPageComponent.CODEC);
    }

}
