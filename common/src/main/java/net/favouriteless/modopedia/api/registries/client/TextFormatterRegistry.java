package net.favouriteless.modopedia.api.registries.client;

import net.favouriteless.modopedia.api.text.TextFormatter;
import net.favouriteless.modopedia.book.registries.client.TextFormatterRegistryImpl;

public interface TextFormatterRegistry {

    static TextFormatterRegistry get() {
        return TextFormatterRegistryImpl.INSTANCE;
    }

    void register(TextFormatter formatter);

}
