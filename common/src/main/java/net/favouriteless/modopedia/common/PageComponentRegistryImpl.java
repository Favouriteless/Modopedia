package net.favouriteless.modopedia.common;

import com.mojang.serialization.Codec;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.books.PageComponent;
import net.favouriteless.modopedia.api.PageComponentRegistry;
import net.favouriteless.modopedia.book.components.TextPageComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class PageComponentRegistryImpl implements PageComponentRegistry {

    public static PageComponentRegistryImpl INSTANCE = new PageComponentRegistryImpl();

    private final Map<ResourceLocation, Codec<? extends PageComponent>> serializersByType = new HashMap<>();

    private PageComponentRegistryImpl() {
        register(Modopedia.id("text"), TextPageComponent.CODEC);
    }

    @Override
    public <T extends PageComponent> void register(ResourceLocation location, Codec<T> codec) {
        if(serializersByType.containsKey(location))
            throw new IllegalArgumentException("Attempted to register a duplicate modopedia page component: " + location.toString());
        serializersByType.put(location, codec);
    }

    @Override
    public Codec<? extends PageComponent> getSerializer(ResourceLocation type) {
        return serializersByType.get(type);
    }

}
