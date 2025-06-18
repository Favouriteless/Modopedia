package net.favouriteless.modopedia.api.datagen.builders.page_components;

import com.google.gson.JsonObject;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.SeparatorPageComponent;

public class SeparatorPageComponentBuilder extends PageComponentBuilder {

    private SeparatorPageComponentBuilder() {
        super(SeparatorPageComponent.ID);
    }

    public static SeparatorPageComponentBuilder of() {
        return new SeparatorPageComponentBuilder();
    }

    @Override
    protected void build(JsonObject json) {}

}
