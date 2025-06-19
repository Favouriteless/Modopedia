package net.favouriteless.modopedia.api.datagen.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.favouriteless.modopedia.datagen.builders.BookContentBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageBuilder extends BookContentBuilder {

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
    protected JsonElement build() {
        JsonObject json = new JsonObject();
        JsonArray components = new JsonArray();
        for(PageComponentBuilder builder : this.components) {
            components.add(builder.build());
        }
        json.add("components", components);

        return json;
    }

}
