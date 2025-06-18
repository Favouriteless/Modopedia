package net.favouriteless.modopedia.api.datagen.builders.page_components.templates;

import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.minecraft.resources.ResourceLocation;

public abstract class TemplatePageComponentBuilder extends PageComponentBuilder {

    private TemplatePageComponentBuilder(ResourceLocation template) {
        super(template, true);
    }

    @Override
    public TemplatePageComponentBuilder x(int x) {
        return (TemplatePageComponentBuilder)super.x(x);
    }

    @Override
    public TemplatePageComponentBuilder x(String reference) {
        return (TemplatePageComponentBuilder)super.x(reference);
    }

    @Override
    public TemplatePageComponentBuilder y(int y) {
        return (TemplatePageComponentBuilder)super.y(y);
    }

    @Override
    public TemplatePageComponentBuilder y(String reference) {
        return (TemplatePageComponentBuilder)super.y(reference);
    }

}
