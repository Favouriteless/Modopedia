package net.favouriteless.modopedia.api.registries;

import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.book.registries.client.PageComponentRegistryImpl;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * Maps {@link PageComponent} IDs to their Suppliers.
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
    void register(ResourceLocation location, Supplier<PageComponent> factory);

    /**
     * Grab the {@link Supplier} for a given type of {@link PageComponent}.
     *
     * @param id The {@link PageComponent}'s ID, as it was registered.
     *
     * @return The {@link Supplier} responsible for type, otherwise null if one wasn't found.
     */
    @Nullable Supplier<PageComponent> get(ResourceLocation id);

}
