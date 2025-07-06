package net.favouriteless.modopedia.api.datagen.builders.page_components.components;

import com.google.gson.*;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.SeparatorPageComponent;

import net.minecraft.resources.RegistryOps;

public class SeparatorBuilder extends PageComponentBuilder {

    private SeparatorBuilder() {
        super(SeparatorPageComponent.ID);
    }

    public static SeparatorBuilder of() {
        return new SeparatorBuilder();
    }

    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {}

}
