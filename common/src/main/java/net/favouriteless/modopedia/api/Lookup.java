package net.favouriteless.modopedia.api;

import java.util.Collection;

public interface Lookup {

    Variable get(String key);

    Variable getOrDefault(String key, Object def);

    boolean has(String key);

    Collection<String> keys();

    /**
     * Mutable version of {@link Lookup} used for template processors.
     */
    interface MutableLookup extends Lookup {

        Variable set(String key, Variable variable);

    }

}