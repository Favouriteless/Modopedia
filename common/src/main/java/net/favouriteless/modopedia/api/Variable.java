package net.favouriteless.modopedia.api;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.book.variables.JsonVariable;
import net.favouriteless.modopedia.book.variables.ObjectVariable;
import net.favouriteless.modopedia.book.variables.RemoteVariable;

import java.util.stream.Stream;

/**
 * Represents a variable within a template or {@link PageComponent}. Has several types:
 * <ul>
 *     <li>{@link JsonVariable} a variable wrapping a {@link JsonElement}.</li>
 *     <li>{@link ObjectVariable} a variable wrapping an {@link Object}.</li>
 *     <li>{@link RemoteVariable} a variable wrapping a key from its parent {@link Lookup}.</li>
 * </ul>
 */
public interface Variable {

    /**
     * Wraps any {@link Object} in a variable.
     */
    static Variable of(Object object) {
        return ObjectVariable.of(object);
    }

    /**
     * @return The value of this Variable as T. May need to register a codec for JsonVariables to decode.
     */
    <T> T as(TypeToken<T> token);

    /**
     * @return The value of this Variable as T. May need to register a codec for JsonVariables to decode.
     */
    <T> T as(Class<T> clazz);

    int asInt();

    long asLong();

    float asFloat();

    double asDouble();

    boolean asBoolean();

    String asString();

    Stream<Variable> asStream();

    /**
     * Register a new {@link Codec} to deserialize types for all JsonVariables.
     *
     * @param token TypeToken representing the type of the codec. Usually fine to pass a {@code new TypeToken<>() {}}.
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

}
