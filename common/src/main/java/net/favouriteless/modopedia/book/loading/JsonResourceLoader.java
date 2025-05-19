package net.favouriteless.modopedia.book.loading;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.favouriteless.modopedia.Modopedia;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

public abstract class JsonResourceLoader {

    protected final Gson gson;
    protected final String dir;

    protected JsonResourceLoader(Gson gson, String dir) {
        this.gson = gson;
        this.dir = dir;
    }

    public CompletableFuture<Void> reload(ResourceManager manager) {
        return CompletableFuture
                .supplyAsync(() -> getResources(manager), Util.backgroundExecutor())
                .thenAcceptAsync(this::load, Minecraft.getInstance());
    }

    protected Map<ResourceLocation, JsonElement> getResources(ResourceManager manager) {
        Map<ResourceLocation, JsonElement> out = new HashMap<>();

        FileToIdConverter converter = FileToIdConverter.json(dir);
        for(Entry<ResourceLocation, Resource> entry : converter.listMatchingResources(manager).entrySet()) {
            try (Reader reader = entry.getValue().openAsReader()) {
                out.put(converter.fileToId(entry.getKey()), GsonHelper.fromJson(gson, reader, JsonElement.class));
            } catch (IllegalArgumentException | IOException | JsonParseException exception) {
                Modopedia.LOG.error("Error trying to read resource {}: {}", entry.getKey(), exception);
            }
        }
        return out;
    }

    protected abstract void load(Map<ResourceLocation, JsonElement> jsonMap);

}
