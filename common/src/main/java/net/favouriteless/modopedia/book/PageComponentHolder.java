package net.favouriteless.modopedia.book;

import net.favouriteless.modopedia.api.Variable;
import net.favouriteless.modopedia.api.Variable.Lookup;
import net.favouriteless.modopedia.api.Variable.MutableLookup;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.book.variables.ObjectVariable;
import net.minecraft.world.level.Level;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PageComponentHolder implements MutableLookup {

    private final Map<PageComponent, Lookup> components = new LinkedHashMap<>();
    private final Map<String, Variable> variables = new HashMap<>();

    public PageComponentHolder() {}

    public void addComponent(PageComponent component, Lookup lookup) {
        components.put(component, lookup);
    }

    public void initComponents(Level level) {
        components.forEach((component, lookup) -> component.init(lookup, level));
    }

    public Collection<PageComponent> getComponents() {
        return components.keySet();
    }

    @Override
    public Variable get(String key) {
        return variables.get(key);
    }

    @Override
    public Variable getOrDefault(String key, Object def) {
        return has(key) ? variables.get(key) : ObjectVariable.of(def);
    }

    @Override
    public boolean has(String key) {
        return variables.containsKey(key);
    }

    @Override
    public Collection<String> keys() {
        return variables.keySet();
    }

    @Override
    public Variable set(String key, Variable variable) {
        return variables.put(key, variable);
    }

}
