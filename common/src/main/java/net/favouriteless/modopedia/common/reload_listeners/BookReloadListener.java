package net.favouriteless.modopedia.common.reload_listeners;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.registries.BookRegistry;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.common.BookRegistryImpl;
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
        BookRegistryImpl.INSTANCE.clear();

        jsonMap.forEach((id, jsonElement) -> {
            try {
                Book.persistentCodec().decode(JsonOps.INSTANCE, jsonElement)
                        .resultOrPartial(error -> Modopedia.LOG.error("Error attempting to load book {}: {}", id, error))
                        .ifPresent(result -> BookRegistry.get().register(id, result.getFirst()));
            }
            catch (IllegalArgumentException | JsonParseException exception) {
                Modopedia.LOG.error("Critical error attempting to load book {}: {}", id, exception.getMessage());
            }
        });
    }

}
