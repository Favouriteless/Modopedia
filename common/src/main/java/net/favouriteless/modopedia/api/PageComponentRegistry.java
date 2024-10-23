package net.favouriteless.modopedia.api;

import com.mojang.serialization.MapCodec;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.api.books.page_components.PageComponentType;
import net.favouriteless.modopedia.book.PageComponentRegistryImpl;
import net.favouriteless.modopedia.book.components.DefaultPageComponents;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * <p>
 *     PageComponentRegistry maps template component types to their serializers. Can be obtained from
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
     * @param factory Factory supplying an instance of a component of this type.
     *
     */
    PageComponentType register(ResourceLocation location, Supplier<PageComponent> factory);

    /**
     * Grab the {@link MapCodec} for a given type of {@link PageComponent}.
     *
     * @param type The {@link PageComponent}'s type, as it was registered.
     *
     * @return The {@link MapCodec} responsible for type, otherwise null if none were found.
     */
    @Nullable PageComponentType get(ResourceLocation id);

}
