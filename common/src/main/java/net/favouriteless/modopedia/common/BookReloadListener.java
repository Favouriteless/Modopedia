package net.favouriteless.modopedia.common;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.book.BookImpl;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public class BookReloadListener extends SimpleJsonResourceReloadListener {

    public BookReloadListener() {
        super(new Gson(), Modopedia.MOD_ID + "/books");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonMap, ResourceManager manager, ProfilerFiller profiler) {
        BookRegistry.INSTANCE.clear();
        jsonMap.forEach((location, jsonElement) -> {
            try {
                if(isRootJson(location))
                    parseRootJson(location, jsonElement);
            } catch (IllegalArgumentException | JsonParseException exception) {
                Modopedia.LOG.error("Error loading book {}: {}", location, exception.getMessage());
            }
        });
    }

    private void parseRootJson(ResourceLocation location, JsonElement jsonElement) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String path = location.getPath();

        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(location.getNamespace(), path.substring(0, path.indexOf("/")));
        ResourceLocation type = jsonObject.has("type") ? ResourceLocation.parse(jsonObject.get("type").getAsString()) : Modopedia.id("default");
        String title = jsonObject.get("title").getAsString();
        String subtitle = jsonObject.has("subtitle") ? jsonObject.get("subtitle").getAsString() : null;
        ResourceLocation texture = jsonObject.has("texture") ? ResourceLocation.parse(jsonObject.get("texture").getAsString()) : Modopedia.id("gui/book/default.png");
        ResourceLocation itemModel = jsonObject.has("itemModel") ? ResourceLocation.parse(jsonObject.get("itemModel").getAsString()) : Modopedia.id("item/book_default");

        BookRegistry.INSTANCE.register(id, new BookImpl(id, type, title, subtitle, texture, itemModel));
        Modopedia.LOG.info("Found book: {}", id);
    }

    private boolean isRootJson(ResourceLocation location) {
        String path = location.getPath();
        String strippedPath = path.substring(path.indexOf("/")+1);
        return !strippedPath.contains("/") && strippedPath.endsWith("book");
    }

}
