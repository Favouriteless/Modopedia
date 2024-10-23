package net.favouriteless.modopedia.book.components;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.PageComponentRegistry;
import net.favouriteless.modopedia.book.PageComponentRegistryImpl.PageComponentType;

public class DefaultPageComponents {

    public static final PageComponentRegistry REGISTRY = PageComponentRegistry.get();

    public static final PageComponentType TEXT = REGISTRY.register(Modopedia.id("text"), TextPageComponent.CODEC);
    public static final PageComponentType IMAGE = REGISTRY.register(Modopedia.id("image"), ImagePageComponent.CODEC);

    public static void load() {

    }

}