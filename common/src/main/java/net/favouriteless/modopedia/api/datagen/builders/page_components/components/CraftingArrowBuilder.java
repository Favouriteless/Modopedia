package net.favouriteless.modopedia.api.datagen.builders.page_components.components;

import com.google.gson.*;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.WidgetPageComponent;

import net.minecraft.resources.RegistryOps;

public class CraftingArrowBuilder extends PageComponentBuilder {

    private CraftingArrowBuilder() {
        super(WidgetPageComponent.ID_CRAFTING_ARROW);
    }

    public static CraftingArrowBuilder of() {
        return new CraftingArrowBuilder();
    }

    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {}

}
