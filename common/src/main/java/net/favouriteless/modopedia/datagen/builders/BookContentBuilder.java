package net.favouriteless.modopedia.datagen.builders;

import com.google.gson.JsonElement;

import net.minecraft.resources.RegistryOps;

public abstract class BookContentBuilder {

    public BookContentBuilder() {}

    public abstract JsonElement build(RegistryOps<JsonElement> ops);

}
