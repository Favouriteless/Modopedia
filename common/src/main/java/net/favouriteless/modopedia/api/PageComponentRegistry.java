package net.favouriteless.modopedia.api;

import com.mojang.serialization.Codec;
import net.favouriteless.modopedia.api.books.PageComponent;
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
    <T extends PageComponent> void register(ResourceLocation location, Codec<T> codec);

    /**
     * Grab the serializer for a given type of {@link PageComponent}.
     *
     * @param type The {@link PageComponent}'s type, as it was registered.
     *
     * @return The {@link Codec} responsible for type, otherwise null if none were found.
     */
    @Nullable
    Codec<? extends PageComponent> getSerializer(ResourceLocation type);

}
