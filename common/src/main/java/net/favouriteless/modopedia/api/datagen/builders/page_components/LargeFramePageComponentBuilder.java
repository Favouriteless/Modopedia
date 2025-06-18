package net.favouriteless.modopedia.api.datagen.builders.page_components;

import com.google.gson.JsonObject;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.WidgetPageComponent;

public class LargeFramePageComponentBuilder extends PageComponentBuilder {

    private LargeFramePageComponentBuilder() {
        super(WidgetPageComponent.ID_LARGE_FRAME);
    }

    public static LargeFramePageComponentBuilder of() {
        return new LargeFramePageComponentBuilder();
    }

    @Override
    protected void build(JsonObject json) {}

}
