package net.favouriteless.modopedia.datagen.builders;

import com.google.gson.JsonElement;

public abstract class BookContentBuilder {

    public BookContentBuilder() {}

    protected abstract JsonElement build();

}
