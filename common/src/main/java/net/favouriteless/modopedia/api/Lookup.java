package net.favouriteless.modopedia.api;

import net.favouriteless.modopedia.api.books.TemplateProcessor;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.book.variables.RemoteVariable;

import java.util.Collection;


/**
 * Wraps a Map of {@link Variable}s for use in {@link PageComponent}s and {@link TemplateProcessor}s.
 */
public interface Lookup {

    /**
     * @return The value attached to the given key. If no value is found, throw an exception.
     */
    Variable get(String key);

    /**
     * @return The value attached to the given key. If no value is found, return the default instead.
     */
    Variable getOrDefault(String key, Object def);

    /**
     * @return True if this Lookup contains a value for key. ({@link RemoteVariable}s will defer up the hierarchy until
     * they find a value)
     */
    boolean has(String key);

    /**
     * @return Collection of all keys present in the lookup.
     */
    Collection<String> keys();



    /**
     * Mutable version of {@link Lookup} used for template processors.
     */
    interface MutableLookup extends Lookup {

        Variable set(String key, Variable variable);

    }

}