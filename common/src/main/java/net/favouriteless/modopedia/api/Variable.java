package net.favouriteless.modopedia.api;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.book.variables.JsonVariable;
import net.favouriteless.modopedia.book.variables.ObjectVariable;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Represents a variable within a template or {@link PageComponent}. The value is stored internally as a
 * {@link JsonElement}.
 */
public interface Variable {

    /**
     * @return The value of this Variable, decoded as T. This could be from a Codec ({@link JsonVariable}) or just a
     * cast ({@link ObjectVariable}).
     */
    <T> T as(TypeToken<T> clazz);

    <T> T as(Class<T> clazz);

    int asInt();

    long asLong();

    float asFloat();

    double asDouble();

    boolean asBoolean();

    String asString();

    Stream<Variable> asStream();

    static Variable of(JsonElement element) {
        return JsonVariable.wrap(element);
    }

    static Variable of(Object object) {
        return ObjectVariable.wrap(object);
    }

    /**
     * Register a new {@link Codec} to deserialize types for all JsonVariables.
     *
     * @param clazz Class of the result.
     * @param codec A codec providing a decoded T given JsonOps and a JsonElement.
     */
    static <T> void registerCodec(Class<T> clazz, Codec<T> codec) {
        JsonVariable.registerCodec(clazz, codec);
    }

    /**
     * Lookup wraps the JsonVariables {@link Map} given to a component.
     */
    interface Lookup {

        Variable get(String key);

        Variable getOrDefault(String key, Object def);

        void set(String key, Variable value);

        boolean has(String key);

        /**
         * @return An <b>IMMUTABLE</b> set containing the keys in this lookup.
         */
        Set<String> getKeys();

    }

}
