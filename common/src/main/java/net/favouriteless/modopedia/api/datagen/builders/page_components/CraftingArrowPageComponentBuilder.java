package net.favouriteless.modopedia.api.datagen.builders.page_components;

import com.google.gson.JsonObject;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.WidgetPageComponent;

public class CraftingArrowPageComponentBuilder extends PageComponentBuilder {

    private CraftingArrowPageComponentBuilder() {
        super(WidgetPageComponent.ID_CRAFTING_ARROW);
    }

    public static CraftingArrowPageComponentBuilder of() {
        return new CraftingArrowPageComponentBuilder();
    }

    @Override
    protected void build(JsonObject json) {}

}
