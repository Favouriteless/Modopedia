package net.favouriteless.modopedia.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.favouriteless.modopedia.api.books.Page;
import net.favouriteless.modopedia.api.books.PageComponent;
import net.favouriteless.modopedia.book.components.DefaultPageComponents;
import net.favouriteless.modopedia.common.PageComponentRegistryImpl;
import net.favouriteless.modopedia.common.PageComponentRegistryImpl.PageComponentType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * <p>
 *     PageComponentRegistry maps template component types to their serializers. Can be obtained from
 *     {@link ModopediaApi#getComponentRegistry()} as well.
 * </p>
 * <p>
 *     See {@link DefaultPageComponents} for usage of the registry.
 * </p>
 */
public interface PageComponentRegistry {

    static PageComponentRegistry get() {
        return PageComponentRegistryImpl.INSTANCE;
    }

    /**
     * Register a Component serializer.
     *
     * @param location Component's type: should match the one used in template JSONs.
     * @param codec Codec used to deserialize components of this type.
     *
     */
    <T extends PageComponent> PageComponentType register(ResourceLocation location, MapCodec<T> codec);

    /**
     * Grab the {@link Codec} for a given type of {@link PageComponent}.
     *
     * @param type The {@link PageComponent}'s type, as it was registered.
     *
     * @return The {@link Codec} responsible for type, otherwise null if none were found.
     */
    @Nullable MapCodec<? extends PageComponent> getCodec(ResourceLocation id);

    /**
     * @return The type codec for the registry. Used to serialize {@link Page}s, most mods shouldn't need this.
     */
    Codec<PageComponent> codec();

}
