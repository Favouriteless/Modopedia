package net.favouriteless.modopedia.api.datagen.builders.page_components.components;

import com.google.gson.*;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.WidgetPageComponent;

import net.minecraft.resources.RegistryOps;

public class MediumFrameBuilder extends PageComponentBuilder {

    private MediumFrameBuilder() {
        super(WidgetPageComponent.ID_MEDIUM_FRAME);
    }

    public static MediumFrameBuilder of() {
        return new MediumFrameBuilder();
    }

    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {}

}
