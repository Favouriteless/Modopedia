package net.favouriteless.modopedia.book.components;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.PageComponentRegistry;
import net.favouriteless.modopedia.book.PageComponentRegistryImpl.PageComponentType;

/**
 * We don't need to bootstrap this class or anything because the registry is never locked, it'll just load when necessary.
 */
public class DefaultPageComponents {

    public static final PageComponentRegistry REGISTRY = PageComponentRegistry.get();

    public static final PageComponentType TEXT = REGISTRY.register(Modopedia.id("text"), TextPageComponent.CODEC);

}
