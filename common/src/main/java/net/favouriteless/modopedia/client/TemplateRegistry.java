package net.favouriteless.modopedia.client;

import com.google.gson.JsonObject;
import net.favouriteless.modopedia.api.books.TemplateProcessor;
import net.favouriteless.modopedia.book.Template;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;

public class TemplateRegistry {

    private static final HashMap<ResourceLocation, Template> templates = new HashMap<>();
    private static final HashMap<ResourceLocation, TemplateProcessor> processors = new HashMap<>();

    public static void createTemplate(ResourceLocation location, JsonObject obj) {
        templates.put(location, new Template(obj));
    }

    public static void createTemplate(ResourceLocation location, TemplateProcessor processor) {
        processors.put(location, processor);
    }

    public static Template getTemplate(ResourceLocation location) {
        return templates.get(location);
    }

    public static TemplateProcessor getProcessor(ResourceLocation location) {
        return processors.get(location);
    }

    public static void clear() {
        templates.clear();
    }

}
