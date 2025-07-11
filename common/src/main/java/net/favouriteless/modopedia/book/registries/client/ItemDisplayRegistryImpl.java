package net.favouriteless.modopedia.book.registries.client;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.page_components.ItemDisplay;
import net.favouriteless.modopedia.api.registries.client.ItemDisplayRegistry;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class ItemDisplayRegistryImpl implements ItemDisplayRegistry {

    public static final ItemDisplayRegistryImpl INSTANCE = new ItemDisplayRegistryImpl();

    private final BiMap<ResourceLocation, MapCodec<? extends ItemDisplay>> codecs = HashBiMap.create();

    private final Codec<MapCodec<? extends ItemDisplay>> typeCodec = ResourceLocation.CODEC.flatXmap(
            r -> {
                MapCodec<? extends ItemDisplay> c = get(r);
                return c != null ? DataResult.success(c) : DataResult.error(() -> "Unknown type " + r);
            },
            c -> {
                ResourceLocation location = codecs.inverse().get(c);
                return c != null ? DataResult.success(location) : DataResult.error(() -> "Unknown type " + location);
            }
    );
    private final Codec<ItemDisplay> codec = typeCodec.dispatch("type", ItemDisplay::typeCodec, Function.identity());


    private ItemDisplayRegistryImpl() {}

    @Override
    public void register(ResourceLocation id, MapCodec<? extends ItemDisplay> codec) {
        if(codecs.containsKey(id))
            Modopedia.LOG.error("Attempted to register duplicate ItemDisplay type: {}", id);
        else
            codecs.put(id, codec);
    }

    @Override
    public MapCodec<? extends ItemDisplay> get(ResourceLocation id) {
        return codecs.get(id);
    }

    @Override
    public Codec<ItemDisplay> codec() {
        return codec;
    }

}
