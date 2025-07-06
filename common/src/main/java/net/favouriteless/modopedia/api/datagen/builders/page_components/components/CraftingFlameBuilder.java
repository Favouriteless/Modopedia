package net.favouriteless.modopedia.api.datagen.builders.page_components.components;

import com.google.gson.*;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.WidgetPageComponent;

import net.minecraft.resources.RegistryOps;

public class CraftingFlameBuilder extends PageComponentBuilder {

    private CraftingFlameBuilder() {
        super(WidgetPageComponent.ID_CRAFTING_FLAME);
    }

    public static CraftingFlameBuilder of() {
        return new CraftingFlameBuilder();
    }

    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {}

}
