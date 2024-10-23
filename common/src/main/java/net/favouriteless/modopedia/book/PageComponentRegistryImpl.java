package net.favouriteless.modopedia.book;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.favouriteless.modopedia.api.PageComponentRegistry;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.api.books.page_components.PageComponentType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class PageComponentRegistryImpl implements PageComponentRegistry {

    public static final PageComponentRegistryImpl INSTANCE = new PageComponentRegistryImpl();

    private final BiMap<ResourceLocation, PageComponentType> serializersByType = HashBiMap.create();

    private PageComponentRegistryImpl() {}

    @Override
    public PageComponentType register(ResourceLocation location, Supplier<PageComponent> factory) {
        if (serializersByType.containsKey(location))
            throw new IllegalArgumentException("Attempted to register a duplicate PageComponent type: " + location.toString());
        PageComponentType type = new PageComponentType(factory);
        serializersByType.put(location, type);
        return type;
    }

    @Override
    public PageComponentType get(ResourceLocation id) {
        return serializersByType.get(id);
    }


}
