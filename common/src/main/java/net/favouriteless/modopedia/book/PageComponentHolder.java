package net.favouriteless.modopedia.book;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.Lookup;
import net.favouriteless.modopedia.api.Lookup.MutableLookup;
import net.favouriteless.modopedia.api.Variable;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.api.registries.BookRegistry;
import net.favouriteless.modopedia.book.page_components.ErrorPageComponent;
import net.favouriteless.modopedia.book.variables.VariableLookup;
import net.minecraft.world.level.Level;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class PageComponentHolder implements MutableLookup {

    private final Map<PageComponent, Lookup> components = new HashMap<>();
    private final VariableLookup lookup = new VariableLookup();

    public PageComponentHolder() {}

    public void addComponent(PageComponent component, Lookup lookup) {
        components.put(component, lookup);
    }

    public void initComponents(Book book, String entryId, Level level) {
        Map<Lookup, String> malformedComponents = new HashMap<>();

        Iterator<Entry<PageComponent, Lookup>> iterator = components.entrySet().iterator();
        while(iterator.hasNext()) {
            Entry<PageComponent, Lookup> entry = iterator.next();
            try {
                entry.getKey().init(book, entry.getValue(), level);
            }
            catch(Exception e) {
                iterator.remove();
                malformedComponents.put(entry.getValue(), e.getMessage());
                Modopedia.LOG.error("Error loading PageComponent of type {} for entry {} of book {}: {}",
                        entry.getKey().getClass().getName(), entryId, BookRegistry.get().getId(book), e.getMessage());
            }
        }

        malformedComponents.forEach((l, e) -> {
            ErrorPageComponent component = new ErrorPageComponent(e);
            components.put(component, l);
            component.init(book, l, level);
        });
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
