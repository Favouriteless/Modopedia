package net.favouriteless.modopedia.client;

import com.google.gson.JsonObject;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.books.TemplateProcessor;
import net.favouriteless.modopedia.api.registries.TemplateRegistry;
import net.favouriteless.modopedia.book.Template;
import net.favouriteless.modopedia.client.template_processors.CookingRecipeProcessor;
import net.favouriteless.modopedia.client.template_processors.CraftingTableProcessor;
import net.favouriteless.modopedia.client.template_processors.FrameSpacingProcessor;
import net.favouriteless.modopedia.client.template_processors.HeaderedTextProcessor;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;

public class TemplateRegistryImpl implements TemplateRegistry {

    public static final TemplateRegistryImpl INSTANCE = new TemplateRegistryImpl();

    private final HashMap<ResourceLocation, Template> templates = new HashMap<>();
    private final HashMap<ResourceLocation, TemplateProcessor> processors = new HashMap<>();

    private TemplateRegistryImpl() {
        registerProcessor(Modopedia.id("headered_text"), new HeaderedTextProcessor());
        registerProcessor(Modopedia.id("small_frame_spacing"), new FrameSpacingProcessor("small_frame"));
        registerProcessor(Modopedia.id("medium_frame_spacing"), new FrameSpacingProcessor("medium_frame"));
        registerProcessor(Modopedia.id("large_frame_spacing"), new FrameSpacingProcessor("large_frame"));
        registerProcessor(Modopedia.id("crafting_grid_spacing"), new FrameSpacingProcessor("crafting_grid"));
        registerProcessor(Modopedia.id("crafting_table_recipe"), new CraftingTableProcessor());
        registerProcessor(Modopedia.id("cooking_recipe"), new CookingRecipeProcessor());
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
