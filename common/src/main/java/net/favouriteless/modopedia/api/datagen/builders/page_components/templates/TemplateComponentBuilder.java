package net.favouriteless.modopedia.api.datagen.builders.page_components.templates;

import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.minecraft.resources.ResourceLocation;

public abstract class TemplateComponentBuilder extends PageComponentBuilder {

    protected TemplateComponentBuilder(ResourceLocation template) {
        super(template, true);
    }

    @Override
    public TemplateComponentBuilder x(int x) {
        return (TemplateComponentBuilder)super.x(x);
    }

    @Override
    public TemplateComponentBuilder x(String x) {
        return (TemplateComponentBuilder)super.x(x);
    }

    @Override
    public TemplateComponentBuilder y(int y) {
        return (TemplateComponentBuilder)super.y(y);
    }

    @Override
    public TemplateComponentBuilder y(String y) {
        return (TemplateComponentBuilder)super.y(y);
    }

}
