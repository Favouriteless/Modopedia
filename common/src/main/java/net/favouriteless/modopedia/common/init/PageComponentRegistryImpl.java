package net.favouriteless.modopedia.common.init;

import com.mojang.serialization.Codec;
import net.favouriteless.modopedia.api.books.Component;
import net.favouriteless.modopedia.api.PageComponentRegistry;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class PageComponentRegistryImpl implements PageComponentRegistry {

    public static PageComponentRegistryImpl INSTANCE = new PageComponentRegistryImpl();

    private final Map<ResourceLocation, Codec<? extends Component>> serializersByType = new HashMap<>();

    private PageComponentRegistryImpl() {

    }

    @Override
    public <T extends Component> void register(ResourceLocation location, Codec<T> codec) {
        if(serializersByType.containsKey(location))
            throw new IllegalArgumentException("Attempted to register a duplicate modopedia component: " + location.toString());
        serializersByType.put(location, codec);
    }

    @Override
    public Codec<? extends Component> getSerializer(ResourceLocation type) {
        return serializersByType.get(type);
    }

}
