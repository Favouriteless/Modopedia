package net.favouriteless.modopedia.api.datagen.builders.page_components;

import com.google.gson.JsonObject;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.WidgetPageComponent;

public class CraftingFlamePageComponentBuilder extends PageComponentBuilder {

    private CraftingFlamePageComponentBuilder() {
        super(WidgetPageComponent.ID_CRAFTING_FLAME);
    }

    public static CraftingFlamePageComponentBuilder of() {
        return new CraftingFlamePageComponentBuilder();
    }

    @Override
    protected void build(JsonObject json) {}

}
