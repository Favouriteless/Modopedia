package net.favouriteless.modopedia.common.reload_listeners;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.book.TemplateRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public class TemplateReloadListener extends SimpleJsonResourceReloadListener {

    public TemplateReloadListener(String directory) {
        super(new Gson(), directory);
    }

    @Override
    protected Map<ResourceLocation, JsonElement> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        return super.prepare(resourceManager, profiler);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonElements, ResourceManager manager, ProfilerFiller profiler) {
        TemplateRegistry.clear();
        jsonElements.forEach((location, element) -> {
            try {
                TemplateRegistry.createTemplate(location, element.getAsJsonObject());
            }
            catch (JsonParseException e) {
                Modopedia.LOG.error("Could not load template {}: {}", location, e);
            }
        });
    }

}
