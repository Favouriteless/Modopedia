package net.favouriteless.modopedia.book.variables;

import com.google.common.reflect.TypeToken;
import net.favouriteless.modopedia.api.Variable;

import java.util.List;
import java.util.stream.Stream;

public class ObjectVariable implements Variable {

    private final Object value;

    private ObjectVariable(Object value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T as(TypeToken<T> token) {
        return (T)value;
    }

    @Override
    public <T> T as(Class<T> clazz) {
        return clazz.cast(value);
    }

    @Override
    public int asInt() {
        return (int)value;
    }

    @Override
    public long asLong() {
        return (int)value;
    }

    @Override
    public float asFloat() {
        return (float)value;
    }

    @Override
    public double asDouble() {
        return (double)value;
    }

    @Override
    public boolean asBoolean() {
        return (boolean)value;
    }

    @Override
    public String asString() {
        return value.toString();
    }

    @Override
    public Stream<Variable> asStream() {
        return ((List<?>)value).stream().map(Variable::of);
    }

    // ------------------------------------ Below this point is non-API functions ------------------------------------

    public static Variable of(Object object) {
        return new ObjectVariable(object);
    }

}
