package net.favouriteless.modopedia.api.datagen.builders.page_components.components;

import com.google.gson.JsonObject;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.WidgetPageComponent;

public class SmallFrameBuilder extends PageComponentBuilder {

    private SmallFrameBuilder() {
        super(WidgetPageComponent.ID_SMALL_FRAME);
    }

    public static SmallFrameBuilder of() {
        return new SmallFrameBuilder();
    }

    @Override
    protected void build(JsonObject json) {}

}
