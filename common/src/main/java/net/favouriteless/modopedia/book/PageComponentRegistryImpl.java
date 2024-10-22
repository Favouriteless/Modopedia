package net.favouriteless.modopedia.book;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import net.favouriteless.modopedia.api.PageComponentRegistry;
import net.favouriteless.modopedia.api.books.PageComponent;
import net.minecraft.resources.ResourceLocation;

public class PageComponentRegistryImpl implements PageComponentRegistry {

    public static final PageComponentRegistryImpl INSTANCE = new PageComponentRegistryImpl();

    private final BiMap<ResourceLocation, PageComponentType> serializersByType = HashBiMap.create();
    private final Codec<PageComponent> codec = ResourceLocation.CODEC.flatXmap(
            loc -> {
                PageComponentType type = serializersByType.get(loc);
                return type != null ? DataResult.success(type) : DataResult.error(() -> "Unknown type " + loc);
            },
            type -> {
                ResourceLocation loc = serializersByType.inverse().get(type);
                return type != null ? DataResult.success(loc) : DataResult.error(() -> "Unknown type " + loc);
            }
    ).dispatch(PageComponent::type, PageComponentType::codec);

    private PageComponentRegistryImpl() {}

    @Override
    public <T extends PageComponent> PageComponentType register(ResourceLocation location, MapCodec<T> codec) {
        if (serializersByType.containsKey(location))
            throw new IllegalArgumentException("Attempted to register a duplicate PageComponent type: " + location.toString());
        PageComponentType type = new PageComponentType(codec);
        serializersByType.put(location, type);
        return type;
    }

    @Override
    public MapCodec<? extends PageComponent> getCodec(ResourceLocation id) {
        return serializersByType.get(id).codec();
    }

    @Override
    public Codec<PageComponent> codec() {
        return codec;
    }

    public record PageComponentType(MapCodec<? extends PageComponent> codec) {}

}
