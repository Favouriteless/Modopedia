package net.favouriteless.modopedia.book.components;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.favouriteless.modopedia.api.Variable;
import net.favouriteless.modopedia.api.Variable.Lookup;
import net.favouriteless.modopedia.api.Variable.MutableLookup;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.book.variables.JsonVariable;
import net.favouriteless.modopedia.book.variables.ObjectVariable;
import net.favouriteless.modopedia.util.BookLoadingUtils;
import net.minecraft.world.level.Level;

import java.util.*;

public class PageComponentHolder implements MutableLookup {

    private final Map<PageComponent, Lookup> components = new LinkedHashMap<>();
    private final Map<String, Variable> variables = new HashMap<>();

    public PageComponentHolder(JsonObject json, int pageNum) {
        createVariables(json, pageNum);

        if(json.has("components"))
            tryCreateComponents(json.getAsJsonArray("components"));
    }

    private void createVariables(JsonObject json, int pageNum) {
        json.keySet().forEach(key -> {
            if(!key.equals("components"))
                variables.put(key, JsonVariable.of(json.get(key)));
        });
        variables.put("pageNum", Variable.of(pageNum));
    }

    public void initComponents() {
        components.forEach(PageComponent::init);
    }

    private void tryCreateComponents(JsonArray array) {
        for(JsonElement element : array) {
            if(element.isJsonObject()) {
                Pair<PageComponent, Lookup> pair = BookLoadingUtils.loadComponent(this, element.getAsJsonObject());
                components.put(pair.getFirst(), pair.getSecond());
            }
        }
    }

    public void onDataReload(Level level) {
        getComponents().forEach(c -> c.refreshData(level, components.get(c)));
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
