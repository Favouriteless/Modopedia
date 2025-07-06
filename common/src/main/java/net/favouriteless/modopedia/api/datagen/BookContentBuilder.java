package net.favouriteless.modopedia.api.datagen;

import com.google.gson.JsonElement;

import net.minecraft.resources.RegistryOps;

public interface BookContentBuilder {

    JsonElement build(RegistryOps<JsonElement> ops);

    default void build(String id, BookContentOutput output) {
        output.accept(id, this);
    }

}
