package net.favouriteless.modopedia.api.datagen.builders.page_components;

import com.google.gson.JsonObject;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.SeparatorPageComponent;

public class SeparatorBuilder extends PageComponentBuilder {

    private SeparatorBuilder() {
        super(SeparatorPageComponent.ID);
    }

    public static SeparatorBuilder of() {
        return new SeparatorBuilder();
    }

    @Override
    protected void build(JsonObject json) {}

}
