package net.favouriteless.modopedia.book.components;

import com.google.gson.*;
import net.favouriteless.modopedia.api.PageComponentRegistry;
import net.favouriteless.modopedia.api.Variable;
import net.favouriteless.modopedia.api.Variable.Lookup;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.api.books.page_components.PageComponentType;
import net.favouriteless.modopedia.book.variables.JsonVariable;
import net.favouriteless.modopedia.book.variables.ObjectVariable;
import net.favouriteless.modopedia.book.variables.RemoteVariable;
import net.favouriteless.modopedia.book.variables.VariableLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PageComponentHolder implements Lookup {

    private final Map<PageComponent, Lookup> components = new LinkedHashMap<>();
    private final Map<String, Variable> variables = new HashMap<>();

    public PageComponentHolder(JsonObject json, int pageNum) {
        createVariables(json, pageNum);

        if(json.has("components"))
            tryCreateComponents(json.getAsJsonArray("components"));

        initComponents();
    }

    private void createVariables(JsonObject json, int pageNum) {
        json.keySet().forEach(key -> {
            if(!key.equals("template") && !key.equals("components"))
                variables.put(key, JsonVariable.of(json.get(key)));
        });
        variables.put("pageNum", Variable.of(pageNum));
    }

    private void initComponents() {
        components.forEach(PageComponent::init);
    }

    private void tryCreateComponents(JsonArray array) {
        for(JsonElement element : array) {
            if(element.isJsonObject())
                createComponent(element.getAsJsonObject());
        }
    }

    private void createComponent(JsonObject jsonObject) {
        ResourceLocation id = ResourceLocation.parse(jsonObject.get("type").getAsString());
        PageComponentType type = PageComponentRegistry.get().get(id);

        if(type == null)
            throw new JsonParseException(String.format("Error creating PageComponent: %s is not a registered component type", id));

        PageComponent component = type.create();
        VariableLookup lookup = new VariableLookup();

        for(String key : jsonObject.keySet()) {
            if(key.equals("type") || key.equals("template"))
                continue;

            JsonElement element = jsonObject.get(key);

            if(element instanceof JsonPrimitive primitive && primitive.isString()) {
                String val = primitive.getAsString();
                if(val.startsWith("#")) {
                    lookup.set(key, RemoteVariable.of(val.substring(1), this));
                    continue;
                }
            }
            lookup.set(key, JsonVariable.of(element));
        }
        lookup.set("pageNum", RemoteVariable.of("pageNum", this));
        components.put(component, lookup);
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

}
