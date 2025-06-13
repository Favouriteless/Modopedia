package net.favouriteless.modopedia.api.registries.client;

import com.google.gson.JsonObject;
import net.favouriteless.modopedia.api.book.TemplateProcessor;
import net.favouriteless.modopedia.book.Template;
import net.favouriteless.modopedia.book.registries.client.TemplateRegistryImpl;
import net.minecraft.resources.ResourceLocation;

/**
 * Maps template IDs to their JSON data and {@link TemplateProcessor}s.
 */
public interface TemplateRegistry {

    static TemplateRegistry get() {
        return TemplateRegistryImpl.INSTANCE;
    }

    void registerTemplate(ResourceLocation id, JsonObject obj);

    void registerProcessor(ResourceLocation id, TemplateProcessor processor);

    Template getTemplate(ResourceLocation id);

    TemplateProcessor getProcessor(ResourceLocation id);

}
