package net.favouriteless.modopedia.api.datagen.builders.page_components;

import com.google.gson.JsonObject;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.WidgetPageComponent;

public class SmallFramePageComponentBuilder extends PageComponentBuilder {

    private SmallFramePageComponentBuilder() {
        super(WidgetPageComponent.ID_SMALL_FRAME);
    }

    public static SmallFramePageComponentBuilder of() {
        return new SmallFramePageComponentBuilder();
    }

    @Override
    protected void build(JsonObject json) {}

}
