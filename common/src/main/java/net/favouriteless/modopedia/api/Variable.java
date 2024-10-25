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
    <T> T as(TypeToken<T> token);

    <T> T as(Class<T> clazz);

    int asInt();

    long asLong();

    float asFloat();

    double asDouble();

    boolean asBoolean();

    String asString();

    Stream<Variable> asStream();

    static Variable of(Object object) {
        return ObjectVariable.of(object);
    }

    /**
     * Register a new {@link Codec} to deserialize types for all JsonVariables.
     *
     * @param token TypeToken representing the type of the codec. Usually fine to pass a new TypeToken<>() {}.
     * @param codec A codec for T.
     */
    static <T> void registerCodec(TypeToken<T> token, Codec<T> codec) {
        JsonVariable.registerCodec(token, codec);
    }

    /**
     * Register a new {@link Codec} to deserialize types for all JsonVariables.
     *
     * @param clazz Class representing the type of the codec.
     * @param codec A codec for T.
     */
    static <T> void registerCodec(Class<T> clazz, Codec<T> codec) {
        JsonVariable.registerCodec(clazz, codec);
    }


    /**
     * Lookup wraps the Variables {@link Map} given to a {@link PageComponent}.
     */
    interface Lookup {

        Variable get(String key);

        Variable getOrDefault(String key, Object def);

        boolean has(String key);

    }

}
