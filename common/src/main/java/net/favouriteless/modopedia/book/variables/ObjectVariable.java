package net.favouriteless.modopedia.book.variables;

import com.google.gson.JsonParseException;
import net.favouriteless.modopedia.api.Variable;

import java.util.List;
import java.util.stream.Stream;

public class ObjectVariable implements Variable {

    private final Object value;

    private ObjectVariable(Object value) {
        this.value = value;
    }

    @Override
    public <T> T as(Class<T> clazz) {
        if(!clazz.isAssignableFrom(value.getClass()))
            throw new JsonParseException(String.format("Could not decode ObjectVariable: %s cannot be cast to %s",
                    value.getClass().getName(), clazz.getName()));

        return clazz.cast(value);
    }

    @Override
    public int asInt() {
        if(!Integer.class.isAssignableFrom(value.getClass()))
            throw new JsonParseException(String.format("Could not decode ObjectVariable: %s cannot be cast to %s",
                    value.getClass().getName(), Integer.class.getName()));
        return (int)value;
    }

    @Override
    public long asLong() {
        if(!Long.class.isAssignableFrom(value.getClass()))
            throw new JsonParseException(String.format("Could not decode ObjectVariable: %s cannot be cast to %s",
                    value.getClass().getName(), Long.class.getName()));
        return (int)value;
    }

    @Override
    public float asFloat() {
        if(!Float.class.isAssignableFrom(value.getClass()))
            throw new JsonParseException(String.format("Could not decode ObjectVariable: %s cannot be cast to %s",
                    value.getClass().getName(), Float.class.getName()));
        return (float)value;
    }

    @Override
    public double asDouble() {
        if(!Double.class.isAssignableFrom(value.getClass()))
            throw new JsonParseException(String.format("Could not decode ObjectVariable: %s cannot be cast to %s",
                    value.getClass().getName(), Double.class.getName()));
        return (double)value;
    }

    @Override
    public boolean asBoolean() {
        if(!Boolean.class.isAssignableFrom(value.getClass()))
            throw new JsonParseException(String.format("Could not decode ObjectVariable: %s cannot be cast to %s",
                    value.getClass().getName(), Boolean.class.getName()));
        return (boolean)value;
    }

    @Override
    public String asString() {
        if(!int.class.isAssignableFrom(value.getClass()))
            throw new JsonParseException(String.format("Could not decode ObjectVariable: %s cannot be cast to %s",
                    value.getClass().getName(), int.class.getName()));
        return value.toString();
    }

    @Override
    public Stream<Variable> asStream() {
        if(value instanceof List<?> list)
            return list.stream().map(Variable::of);
        throw new JsonParseException("Could not convert ObjectVariable to stream: not backed by a list");
    }

    // ------------------------------------ Below this point is non-API functions ------------------------------------

    public static Variable wrap(Object object) {
        return new ObjectVariable(object);
    }

}
