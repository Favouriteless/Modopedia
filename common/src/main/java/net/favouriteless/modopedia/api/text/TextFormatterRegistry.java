package net.favouriteless.modopedia.api.text;

import net.favouriteless.modopedia.api.ModopediaApi;
import net.favouriteless.modopedia.book.text.TextFormatterRegistryImpl;

/**
 * <p>
 *     TextFormatterRegistry is the registry for text formatting tags in books (e.g. $(b)). Can be obtained from
 *     {@link ModopediaApi#getTextFormatterRegistry()}
 * </p>
 * <p>
 *     See {@link TextFormatterRegistryImpl} for usage of the registry.
 * </p>
 */
public interface TextFormatterRegistry {

    /**
     * Register a {@link TextFormatter}.
     */
    void register(TextFormatter formatter);

}
