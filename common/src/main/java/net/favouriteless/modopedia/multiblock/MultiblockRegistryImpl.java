package net.favouriteless.modopedia.multiblock;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.multiblock.Multiblock;
import net.favouriteless.modopedia.api.registries.MultiblockRegistry;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MultiblockRegistryImpl implements MultiblockRegistry {

    public static final MultiblockRegistryImpl INSTANCE = new MultiblockRegistryImpl();

    private final BiMap<ResourceLocation, MapCodec<? extends Multiblock>> codecs = HashBiMap.create();
    private final Map<ResourceLocation, Multiblock> multiblocks = new HashMap<>();

    private final Codec<MapCodec<? extends Multiblock>> typeCodec = ResourceLocation.CODEC.flatXmap(
            r -> {
                MapCodec<? extends Multiblock> c = getType(r);
                return c != null ? DataResult.success(c) : DataResult.error(() -> "Unknown type " + r);
            },
            c -> {
                ResourceLocation location = codecs.inverse().get(c);
                return c != null ? DataResult.success(location) : DataResult.error(() -> "Unknown type " + location);
            }
    );
    private final Codec<Multiblock> codec = typeCodec.dispatch("type", Multiblock::typeCodec, Function.identity());


    private MultiblockRegistryImpl() {
        registerType(Modopedia.id("dense"), DenseMultiblock.CODEC);
    }

    @Override
    public void register(ResourceLocation id, Multiblock multiblock) {
        multiblocks.put(id, multiblock);
    }

    @Override
    public @Nullable Multiblock get(ResourceLocation id) {
        return multiblocks.get(id);
    }

    @Override
    public void registerType(ResourceLocation id, MapCodec<? extends Multiblock> codec) {
        if(codecs.containsKey(id))
            Modopedia.LOG.error("Attempted to register duplicate Multiblock type: {}", id);
        else
            codecs.put(id, codec);
    }

    @Override
    public @Nullable MapCodec<? extends Multiblock> getType(ResourceLocation id) {
        return codecs.get(id);
    }

    @Override
    public Codec<Multiblock> codec() {
        return codec;
    }

}
