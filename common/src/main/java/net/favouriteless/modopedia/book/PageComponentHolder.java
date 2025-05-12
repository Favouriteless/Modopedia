package net.favouriteless.modopedia.book;

import net.favouriteless.modopedia.api.Variable;
import net.favouriteless.modopedia.api.Variable.Lookup;
import net.favouriteless.modopedia.api.Variable.MutableLookup;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.book.variables.VariableLookup;
import net.minecraft.world.level.Level;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class PageComponentHolder implements MutableLookup {

    private final Map<PageComponent, Lookup> components = new LinkedHashMap<>();
    private final VariableLookup lookup = new VariableLookup();

    public PageComponentHolder() {}

    public void addComponent(PageComponent component, Lookup lookup) {
        components.put(component, lookup);
    }

    public void initComponents(Book book, Level level) {
        components.forEach((component, lookup) -> component.init(book, lookup, level));
    }

    public Collection<PageComponent> getComponents() {
        return components.keySet();
    }

    @Override
    public Variable set(String key, Variable variable) {
        return lookup.set(key, variable);
    }

    @Override
    public Variable get(String key) {
        return lookup.get(key);
    }

    @Override
    public Variable getOrDefault(String key, Object def) {
        return lookup.getOrDefault(key, def);
    }

    @Override
    public boolean has(String key) {
        return lookup.has(key);
    }

    @Override
    public Collection<String> keys() {
        return lookup.keys();
    }

}
