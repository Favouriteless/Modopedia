package net.favouriteless.modopedia.book.variables;

import net.favouriteless.modopedia.api.Variable;

import java.util.*;

public class VariableLookup implements Variable.Lookup {

    private final Map<String, Variable> internalMap = new HashMap<>();;

    @Override
    public Variable get(String key) {
        return internalMap.get(key);
    }

    @Override
    public Variable getOrDefault(String key, Object def) {
        return has(key) ? get(key) : Variable.of(def);
    }

    @Override
    public boolean has(String key) {
        return internalMap.containsKey(key);
    }

    @Override
    public Collection<String> keys() {
        return internalMap.keySet();
    }

    public void set(String key, Variable variable) {
        internalMap.put(key, variable);
    }

}