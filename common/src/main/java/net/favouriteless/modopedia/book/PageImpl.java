package net.favouriteless.modopedia.book;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.favouriteless.modopedia.api.PageComponentRegistry;
import net.favouriteless.modopedia.api.Variable;
import net.favouriteless.modopedia.api.Variable.Lookup;
import net.favouriteless.modopedia.api.books.Page;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.api.books.page_components.PageComponentType;
import net.favouriteless.modopedia.book.variables.VariableLookup;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class PageImpl implements Page {

    private final Map<PageComponent, Lookup> components = new LinkedHashMap<>();
    private final Lookup variables = new VariableLookup();

    public PageImpl(JsonObject fullJson) {
        for(String key : fullJson.keySet()) {
            if(!key.equals("template"))
                variables.set(key, Variable.of(fullJson.get(key)));
        }

        if(fullJson.has("components"))
            tryCreateComponents(fullJson.getAsJsonArray("components"));

        refreshSharedVariables();
        initComponents();
    }

    private void initComponents() {
        components.forEach(PageComponent::init);
    }

    private void refreshSharedVariables() {
        components.forEach((component, lookup) -> {

        });
    }

    private void tryCreateComponents(JsonArray array) {
        for(JsonElement element : array) {
            if(element.isJsonObject())
                createComponent(element.getAsJsonObject());
        }
    }

    private void createComponent(JsonObject jsonObject) {
        ResourceLocation id = ResourceLocation.parse(jsonObject.getAsJsonObject("type").getAsString());
        PageComponentType type = PageComponentRegistry.get().get(id);

        if(type == null)
            throw new JsonParseException(String.format("Error creating PageComponent: %s is not a registered component type", id));

        PageComponent component = type.create();
        Lookup componentVars = new VariableLookup();

        for(String key : jsonObject.keySet()) {
            if(!key.equals("id"))
                componentVars.set(key, Variable.of(jsonObject.get(key)));
        }
        components.put(component, componentVars);
    }

    @Override
    public Collection<PageComponent> getComponents() {
        return components.keySet();
    }

    // ------------------------------------ Below this point is non-API functions ------------------------------------

}
