package net.favouriteless.modopedia.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.mojang.datafixers.util.Pair;
import net.favouriteless.modopedia.api.PageComponentRegistry;
import net.favouriteless.modopedia.api.Variable.Lookup;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.api.books.page_components.PageComponentType;
import net.favouriteless.modopedia.book.components.TemplatePageComponent;
import net.favouriteless.modopedia.book.variables.JsonVariable;
import net.favouriteless.modopedia.book.variables.RemoteVariable;
import net.favouriteless.modopedia.book.variables.VariableLookup;
import net.minecraft.resources.ResourceLocation;

public class BookLoadingUtils {

    public static Pair<PageComponent, Lookup> loadComponent(Lookup parent, JsonObject json) {
        PageComponent component;
        if(json.has("type")) {
            ResourceLocation id = ResourceLocation.parse(json.get("type").getAsString());
            PageComponentType type = PageComponentRegistry.get().get(id);

            if(type == null)
                throw new JsonParseException(String.format("Error creating PageComponent: %s is not a registered component type", id));

            component = type.create();
        }
        else {
            component = new TemplatePageComponent(); // templates get special handling
        }

        VariableLookup lookup = new VariableLookup();
        lookup.set("page_num", RemoteVariable.of("page_num", parent));

        for(String key : json.keySet()) {
            if(key.equals("type"))
                continue;

            JsonElement element = json.get(key);

            if(element instanceof JsonPrimitive primitive && primitive.isString()) {
                String val = primitive.getAsString();
                if(val.startsWith("#")) {
                    lookup.set(key, RemoteVariable.of(val.substring(1), parent));
                    continue;
                }
            }
            lookup.set(key, JsonVariable.of(element));
        }

        return Pair.of(component, lookup);
    }

}
