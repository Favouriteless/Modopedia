package net.favouriteless.modopedia.book.variables;

import net.favouriteless.modopedia.api.Variable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VariableLookup implements Variable.Lookup {

    private final Map<String, Variable> internalMap;

    public VariableLookup(Map<String, Variable> variables) {
        this.internalMap = variables;
    }

    public VariableLookup() {
        this.internalMap = new HashMap<>();
    }

    @Override
    public Variable get(String key) {
        return internalMap.get(key);
    }

    @Override
    public Variable getOrDefault(String key, Object def) {
        return has(key) ? get(key) : Variable.of(def);
    }

    @Override
    public void set(String key, Variable value) {
        internalMap.put(key, value);
    }

    @Override
    public boolean has(String key) {
        return internalMap.containsKey(key);
    }

    @Override
    public Set<String> getKeys() {
        return internalMap.keySet();
    }

}