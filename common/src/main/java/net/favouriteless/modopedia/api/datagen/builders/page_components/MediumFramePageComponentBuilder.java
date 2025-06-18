package net.favouriteless.modopedia.api.datagen.builders.page_components;

import com.google.gson.JsonObject;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.WidgetPageComponent;

public class MediumFramePageComponentBuilder extends PageComponentBuilder {

    private MediumFramePageComponentBuilder() {
        super(WidgetPageComponent.ID_MEDIUM_FRAME);
    }

    public static MediumFramePageComponentBuilder of() {
        return new MediumFramePageComponentBuilder();
    }

    @Override
    protected void build(JsonObject json) {}

}
