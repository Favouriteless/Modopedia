package net.favouriteless.modopedia.api;

import com.mojang.serialization.Codec;
import net.favouriteless.modopedia.api.books.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * <p>
 *     ComponentRegistry maps template component types to their serializers.
 * </p>
 * Can be obtained from {@link ModopediaApi#getComponentRegistry()}
 */
public interface PageComponentRegistry {

    /**
     * Register a Component serializer.
     *
     * @param location Component's type: should match the one used in template JSONs.
     * @param codec Codec used to deserialize components of this type.
     *
     */
    <T extends Component> void register(ResourceLocation location, Codec<T> codec);

    /**
     * Grab the serializer for a given type of {@link Component}.
     *
     * @param type The {@link Component}'s type, as it was registered.
     *
     * @return The {@link Codec} responsible for type, otherwise null if none were found.
     */
    @Nullable
    Codec<? extends Component> getSerializer(ResourceLocation type);

}
