package net.favouriteless.modopedia.book.registries.client;

import com.google.gson.JsonObject;
import net.favouriteless.modopedia.api.book.TemplateProcessor;
import net.favouriteless.modopedia.api.registries.client.TemplateRegistry;
import net.favouriteless.modopedia.book.Template;
import net.favouriteless.modopedia.client.template_processors.CookingRecipeProcessor;
import net.favouriteless.modopedia.client.template_processors.CraftingTableRecipeProcessor;
import net.favouriteless.modopedia.client.template_processors.FrameSpacingProcessor;
import net.favouriteless.modopedia.client.template_processors.HeaderedTextProcessor;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;

public class TemplateRegistryImpl implements TemplateRegistry {

    public static final TemplateRegistryImpl INSTANCE = new TemplateRegistryImpl();

    private final HashMap<ResourceLocation, Template> templates = new HashMap<>();
    private final HashMap<ResourceLocation, TemplateProcessor> processors = new HashMap<>();

    private TemplateRegistryImpl() {
        registerProcessor(HeaderedTextProcessor.ID, new HeaderedTextProcessor());
        registerProcessor(FrameSpacingProcessor.ID_SMALL, new FrameSpacingProcessor("small_frame"));
        registerProcessor(FrameSpacingProcessor.ID_MEDIUM, new FrameSpacingProcessor("medium_frame"));
        registerProcessor(FrameSpacingProcessor.ID_LARGE, new FrameSpacingProcessor("large_frame"));
        registerProcessor(FrameSpacingProcessor.ID_CRAFTING, new FrameSpacingProcessor("crafting_grid"));
        registerProcessor(CraftingTableRecipeProcessor.ID, new CraftingTableRecipeProcessor());
        registerProcessor(CookingRecipeProcessor.ID, new CookingRecipeProcessor());
    }

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
