package net.favouriteless.modopedia.book;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.PageComponentRegistry;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.book.page_components.*;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class PageComponentRegistryImpl implements PageComponentRegistry {

    public static final PageComponentRegistry INSTANCE = new PageComponentRegistryImpl();

    private final BiMap<ResourceLocation, Supplier<PageComponent>> serializersByType = HashBiMap.create();

    private PageComponentRegistryImpl() {
        register(Modopedia.id("text"), TextPageComponent::new);
        register(Modopedia.id("image"), ImagePageComponent::new);
        register(Modopedia.id("tooltip"), TooltipPageComponent::new);
        register(Modopedia.id("header"), HeaderPageComponent::new);
        register(Modopedia.id("separator"), SeparatorPageComponent::new);
    }

    @Override
    public void register(ResourceLocation location, Supplier<PageComponent> factory) {
        if (serializersByType.containsKey(location))
            throw new IllegalArgumentException("Attempted to register a duplicate PageComponent type: " + location.toString());
        serializersByType.put(location, factory);
    }

    @Override
    public Supplier<PageComponent> get(ResourceLocation id) {
        return serializersByType.get(id);
    }


}
