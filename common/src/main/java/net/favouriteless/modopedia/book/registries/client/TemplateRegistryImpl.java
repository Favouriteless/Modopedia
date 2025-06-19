package net.favouriteless.modopedia.book.registries.client;

import com.google.gson.JsonObject;
import net.favouriteless.modopedia.api.book.TemplateProcessor;
import net.favouriteless.modopedia.api.registries.client.TemplateRegistry;
import net.favouriteless.modopedia.book.Template;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;

public class TemplateRegistryImpl implements TemplateRegistry {

    public static final TemplateRegistryImpl INSTANCE = new TemplateRegistryImpl();

    private final HashMap<ResourceLocation, Template> templates = new HashMap<>();
    private final HashMap<ResourceLocation, TemplateProcessor> processors = new HashMap<>();

    private TemplateRegistryImpl() {}

    @Override
    public void registerTemplate(ResourceLocation id, JsonObject obj) {
        if(templates.containsKey(id))
            throw new IllegalArgumentException("Attempted to register a duplicate Modopedia template: " + id.toString());
        templates.put(id, new Template(obj));
    }

    @Override
    public void registerProcessor(ResourceLocation id, TemplateProcessor processor) {
        if(processors.containsKey(id))
            throw new IllegalArgumentException("Attempted to register a duplicate Modopedia template processor: " + id.toString());
        processors.put(id, processor);
    }

    @Override
    public Template getTemplate(ResourceLocation location) {
        return templates.get(location);
    }

    @Override
    public TemplateProcessor getProcessor(ResourceLocation location) {
        return processors.get(location);
    }

    public void clear() {
        templates.clear();
    }

}
