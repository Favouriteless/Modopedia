package net.favouriteless.modopedia.api;

import net.favouriteless.modopedia.api.text.TextFormatter;
import net.favouriteless.modopedia.book.text.TextFormatterRegistryImpl;

/**
 * <p>
 *     TextFormatterRegistry is used for the text formatting tags in books (e.g. $(b)). Can be obtained from
 *     {@link ModopediaApi#getTextFormatterRegistry()}
 * </p>
 * <p>
 *     See {@link TextFormatterRegistryImpl} for usage of the registry.
 * </p>
 */
public interface TextFormatterRegistry {

    static TextFormatterRegistry get() {
        return TextFormatterRegistryImpl.INSTANCE;
    }

    /**
     * Register a {@link TextFormatter}.
     */
    void register(TextFormatter formatter);

}
