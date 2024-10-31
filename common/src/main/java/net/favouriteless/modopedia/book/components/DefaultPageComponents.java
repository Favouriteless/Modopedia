package net.favouriteless.modopedia.book.components;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.PageComponentRegistry;
import net.favouriteless.modopedia.api.books.page_components.PageComponentType;

public class DefaultPageComponents {

    public static final PageComponentRegistry REGISTRY = PageComponentRegistry.get();

    public static final PageComponentType TEXT = REGISTRY.register(Modopedia.id("text"), TextPageComponent::new);
    public static final PageComponentType IMAGE = REGISTRY.register(Modopedia.id("image"), ImagePageComponent::new);
    public static final PageComponentType TOOLTIP = REGISTRY.register(Modopedia.id("tooltip"), TooltipPageComponent::new);

    public static void load() {

    }

}