package net.favouriteless.modopedia.book.registries.client;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.favouriteless.modopedia.api.book.page_components.PageComponent;
import net.favouriteless.modopedia.api.registries.client.PageComponentRegistry;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class PageComponentRegistryImpl implements PageComponentRegistry {

    public static final PageComponentRegistry INSTANCE = new PageComponentRegistryImpl();

    private final BiMap<ResourceLocation, Supplier<PageComponent>> serializersByType = HashBiMap.create();

    private PageComponentRegistryImpl() {}

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
