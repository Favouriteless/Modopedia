package net.favouriteless.modopedia.client.reload_listeners;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.ModopediaApiImpl;
import net.favouriteless.modopedia.api.books.BookContentManager;
import net.favouriteless.modopedia.api.books.Category;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.api.books.Page;
import net.favouriteless.modopedia.book.BookContentImpl;
import net.favouriteless.modopedia.book.BookContentImpl.LocalisedBookContent;
import net.favouriteless.modopedia.book.CategoryImpl;
import net.favouriteless.modopedia.book.EntryImpl;
import net.favouriteless.modopedia.book.PageImpl;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class BookContentReloadListener implements PreparableReloadListener {

    private static final Gson GSON = new Gson();
    private final String directory;

    public BookContentReloadListener(String directory) {
        this.directory = directory;
    }

    @Override
    public final CompletableFuture<Void> reload(PreparationBarrier stage, ResourceManager manager,
                                                ProfilerFiller prepProfiler, ProfilerFiller reloadProfiler,
                                                Executor backgroundExecutor, Executor gameExecutor) {
        ModopediaApiImpl.isLoading = true;
        return CompletableFuture
                .supplyAsync(() -> this.prepare(manager, prepProfiler, backgroundExecutor), backgroundExecutor)
                .thenCompose(stage::wait)
                .thenAcceptAsync(this::registerBooks, gameExecutor);
    }

    private Map<ResourceLocation, Map<String, LocalisedBookContent>> prepare(ResourceManager manager, ProfilerFiller profiler, Executor executor) {


        return Map.of();
    }

    protected Map<String, Category> constructCategories(Map<String, JsonElement> jsonElements) {
        Map<String, Category> out = new HashMap<>();

        jsonElements.forEach((id, json) ->
                CategoryImpl.CODEC.decode(JsonOps.INSTANCE, json)
                        .resultOrPartial(error -> Modopedia.LOG.error("Error loading category {}: {}", id, error))
                        .ifPresent(pair -> out.put(id, pair.getFirst()))
        );

        return out;
    }

    protected Map<String, Entry> constructEntries(Map<String, JsonElement> jsonElements) {
        Map<String, Entry> out = new HashMap<>();

        jsonElements.forEach((id, json) -> EntryImpl.CODEC.decode(JsonOps.INSTANCE, json)
                .resultOrPartial(error -> Modopedia.LOG.error("Error loading entry {}: {}", id, error))
                .ifPresent(p -> out.put(id, p.getFirst().addPages(constructPages(json.getAsJsonObject().getAsJsonArray("pages")))))
        );

        return out;
    }

    protected Page[] constructPages(JsonArray array) {
        Page[] out = new Page[array.size()];
        for(int i = 0; i < out.length; i++)
            out[i] = new PageImpl(array.get(i).getAsJsonObject());
        return out;
    }
    
    protected void registerBooks(Map<ResourceLocation, Map<String, LocalisedBookContent>> booksMap) {
        BookContentManager.get().clear();
        booksMap.forEach((id, contentMap) -> BookContentManager.get().register(id, new BookContentImpl(contentMap)));
        ModopediaApiImpl.isLoading = false;
    }

}
