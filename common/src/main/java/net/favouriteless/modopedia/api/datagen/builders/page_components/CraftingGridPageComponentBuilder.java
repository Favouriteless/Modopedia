package net.favouriteless.modopedia.api.datagen.builders.page_components;

import com.google.gson.JsonObject;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.WidgetPageComponent;

public class CraftingGridPageComponentBuilder extends PageComponentBuilder {

    private CraftingGridPageComponentBuilder() {
        super(WidgetPageComponent.ID_CRAFTING_GRID);
    }

    public static CraftingGridPageComponentBuilder of() {
        return new CraftingGridPageComponentBuilder();
    }

    @Override
    protected void build(JsonObject json) {}

}
