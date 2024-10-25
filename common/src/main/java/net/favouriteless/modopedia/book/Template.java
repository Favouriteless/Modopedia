package net.favouriteless.modopedia.book;

import com.google.gson.JsonObject;

public class Template {

    private final JsonObject components;

    public Template(JsonObject object) {
        this.components = object;
    }

    public JsonObject getData() {
        return components;
    }

}
