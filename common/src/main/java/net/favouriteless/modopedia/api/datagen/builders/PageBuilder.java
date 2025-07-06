package net.favouriteless.modopedia.api.datagen.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.favouriteless.modopedia.api.datagen.BookContentBuilder;

import net.minecraft.resources.RegistryOps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageBuilder implements BookContentBuilder {

    private final List<PageComponentBuilder> components = new ArrayList<>();

    protected PageBuilder() {}

    public static PageBuilder of() {
        return new PageBuilder();
    }

    public PageBuilder components(PageComponentBuilder... components) {
        this.components.addAll(Arrays.asList(components));
        return this;
    }

    @Override
    public JsonElement build(RegistryOps<JsonElement> ops) {
        JsonObject json = new JsonObject();
        JsonArray components = new JsonArray();
        for(PageComponentBuilder builder : this.components) {
            components.add(builder.build(ops));
        }
        json.add("components", components);

        return json;
    }

}
