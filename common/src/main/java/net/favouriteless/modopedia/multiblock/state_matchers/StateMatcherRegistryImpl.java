package net.favouriteless.modopedia.multiblock.state_matchers;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.multiblock.StateMatcher;
import net.favouriteless.modopedia.api.registries.StateMatcherRegistry;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class StateMatcherRegistryImpl implements StateMatcherRegistry {

    public static final StateMatcherRegistryImpl INSTANCE = new StateMatcherRegistryImpl();

    private final BiMap<ResourceLocation, MapCodec<? extends StateMatcher>> codecs = HashBiMap.create();

    private StateMatcherRegistryImpl() {
        register(Modopedia.id("air"), AirStateMatcher.CODEC);
        register(Modopedia.id("simple"), SimpleStateMatcher.CODEC);
        register(Modopedia.id("tag"), TagStateMatcher.CODEC);
        register(Modopedia.id("either"), EitherStateMatcher.CODEC);
        register(Modopedia.id("both"), BothStateMatcher.CODEC);
    }

    @Override
    public void register(ResourceLocation id, MapCodec<? extends StateMatcher> codec) {
        if(codecs.containsKey(id))
            Modopedia.LOG.error("Attempted to register duplicate BlockStateMatcher codec: {}", id);
        else
            codecs.put(id, codec);
    }

    @Override
    public MapCodec<? extends StateMatcher> get(ResourceLocation id) {
        return codecs.get(id);
    }

    private static final Codec<MapCodec<? extends StateMatcher>> TYPE_CODEC = ResourceLocation.CODEC.flatXmap(
            r -> {
                MapCodec<? extends StateMatcher> c = StateMatcherRegistry.get().get(r);
                return c != null ? DataResult.success(c) : DataResult.error(() -> "Unknown type " + r);
            },
            c -> {
                ResourceLocation location = INSTANCE.codecs.inverse().get(c);
                return c != null ? DataResult.success(location) : DataResult.error(() -> "Unknown type " + location);
            }
    );

    public static final Codec<StateMatcher> CODEC = TYPE_CODEC.dispatch("type", StateMatcher::codec, Function.identity());

}
