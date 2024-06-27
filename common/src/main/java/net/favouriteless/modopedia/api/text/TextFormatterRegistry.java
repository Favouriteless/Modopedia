package net.favouriteless.modopedia.api.text;

import net.favouriteless.modopedia.api.ModopediaApi;

/**
 * <p>
 *     TextFormatterRegistry is a way to register handlers for text formatting tags in books (i.e. $(b)).
 * </p>
 * Can be obtained from {@link ModopediaApi#getTextFormatterRegistry()}
 */
public interface TextFormatterRegistry {

    /**
     * Register a {@link TextFormatter}.
     */
    void register(TextFormatter formatter);

}
