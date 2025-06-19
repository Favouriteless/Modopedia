package net.favouriteless.modopedia.datagen;

import com.google.gson.JsonElement;

public abstract class BookContentBuilder {

    public BookContentBuilder() {}

    protected abstract JsonElement build();

}
