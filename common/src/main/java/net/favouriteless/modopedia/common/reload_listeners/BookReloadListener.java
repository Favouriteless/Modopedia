package net.favouriteless.modopedia.common.reload_listeners;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.BookRegistry;
import net.favouriteless.modopedia.api.books.Book;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public class BookReloadListener extends SimpleJsonResourceReloadListener {

    public BookReloadListener(String directory) {
        super(new Gson(), directory);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonMap, ResourceManager manager, ProfilerFiller profiler) {
        BookRegistry.get().clear();

        jsonMap.forEach((location, jsonElement) -> {
            try {
                ResourceLocation id = location;
                Book.persistentCodec().decode(JsonOps.INSTANCE, jsonElement)
                        .resultOrPartial(error -> Modopedia.LOG.error("Error attempting to load book {}: {}", location, error))
                        .ifPresent(result -> BookRegistry.get().register(id, result.getFirst()));
            }
            catch (IllegalArgumentException | JsonParseException exception) {
                Modopedia.LOG.error("Critical error attempting to load book {}: {}", location, exception.getMessage());
            }
        });
    }

}
