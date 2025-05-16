package net.favouriteless.modopedia;

import net.favouriteless.modopedia.api.ModopediaApi;
import net.favouriteless.modopedia.api.books.TemplateProcessor;
import net.favouriteless.modopedia.api.text.TextFormatter;
import net.favouriteless.modopedia.book.text.TextFormatterRegistry;
import net.favouriteless.modopedia.client.ItemAssociationRegistry;
import net.favouriteless.modopedia.client.TemplateRegistry;
import net.minecraft.resources.ResourceLocation;

public class ModopediaApiImpl implements ModopediaApi {

    public static final ModopediaApi INSTANCE = new ModopediaApiImpl();

    private ModopediaApiImpl() {}

    @Override
    public void registerTemplateProcessor(ResourceLocation id, TemplateProcessor processor) {
        TemplateRegistry.createTemplate(id, processor);
    }

    @Override
    public void registerTextFormatter(TextFormatter formatter) {
        TextFormatterRegistry.register(formatter);
    }

    @Override
    public EntryAssociation getAssociation(String language, ResourceLocation item) {
        return ItemAssociationRegistry.getAssociation(language, item);
    }

}
