package net.favouriteless.modopedia.book.variables;

import com.google.common.reflect.TypeToken;
import net.favouriteless.modopedia.api.Lookup;
import net.favouriteless.modopedia.api.Variable;

import java.util.stream.Stream;

public class RemoteVariable implements Variable {

    private final String key;
    private final Lookup lookup;

    private RemoteVariable(String key, Lookup lookup) {
        this.key = key;
        this.lookup = lookup;
    }

    @Override
    public <T> T as(TypeToken<T> token) {
        return lookup.get(key).as(token);
    }

    @Override
    public <T> T as(Class<T> clazz) {
        return lookup.get(key).as(clazz);
    }

    @Override
    public int asInt() {
        return lookup.get(key).asInt();
    }

    @Override
    public long asLong() {
        return lookup.get(key).asLong();
    }

    @Override
    public float asFloat() {
        return lookup.get(key).asFloat();
    }

    @Override
    public double asDouble() {
        return lookup.get(key).asDouble();
    }

    @Override
    public boolean asBoolean() {
        return lookup.get(key).asBoolean();
    }

    @Override
    public String asString() {
        return lookup.get(key).asString();
    }

    @Override
    public Stream<Variable> asStream() {
        return lookup.get(key).asStream();
    }

    // ------------------------------------ Below this point is non-API functions ------------------------------------

    public static Variable of(String key, Lookup lookup) {
        return new RemoteVariable(key, lookup);
    }

    public boolean hasValue() {
        return lookup.has(key);
    }

}
