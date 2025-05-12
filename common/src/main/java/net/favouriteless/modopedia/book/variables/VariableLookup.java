package net.favouriteless.modopedia.book.variables;

import net.favouriteless.modopedia.api.Variable;
import net.favouriteless.modopedia.api.Variable.MutableLookup;

import java.util.*;

public class VariableLookup implements MutableLookup {

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
        return internalMap.containsKey(key) && (!(get(key) instanceof RemoteVariable remote) || remote.has(key));
    }

    @Override
    public Collection<String> keys() {
        return internalMap.keySet();
    }

    @Override
    public Variable set(String key, Variable variable) {
        internalMap.put(key, variable);
        return variable;
    }

}