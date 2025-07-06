package net.favouriteless.modopedia.api.datagen.builders.page_components.components;

import com.google.gson.*;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.WidgetPageComponent;

import net.minecraft.resources.RegistryOps;

public class LargeFrameBuilder extends PageComponentBuilder {

    private LargeFrameBuilder() {
        super(WidgetPageComponent.ID_LARGE_FRAME);
    }

    public static LargeFrameBuilder of() {
        return new LargeFrameBuilder();
    }

    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {}

}
