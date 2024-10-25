package net.favouriteless.modopedia.client.reload_listeners;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.ModopediaApiImpl;
import net.favouriteless.modopedia.api.books.BookContent;
import net.favouriteless.modopedia.api.books.BookContentManager;
import net.favouriteless.modopedia.api.books.Category;
import net.favouriteless.modopedia.api.books.Page;
import net.favouriteless.modopedia.book.BookContentImpl;
import net.favouriteless.modopedia.book.BookContentImpl.LocalisedBookContent;
import net.favouriteless.modopedia.book.CategoryImpl;
import net.favouriteless.modopedia.book.EntryImpl;
import net.favouriteless.modopedia.book.PageImpl;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

public class BookContentReloadListener extends SimplePreparableReloadListener<Map<ResourceLocation, Map<String, LocalisedBookContent>>> {

    private static final Gson GSON = new Gson();
    private final String directory;

    public BookContentReloadListener(String directory) {
        this.directory = directory;
    }

    @Override
    protected Map<ResourceLocation, Map<String, LocalisedBookContent>> prepare(ResourceManager manager, ProfilerFiller profiler) {
        Map<ResourceLocation, JsonElement> resources = scanDirectory(manager, directory, GSON);

        Map<ResourceLocation, Map<String, LocalisedBookContent>> out = new HashMap<>();
        resources.forEach((location, json) -> {
            String[] splitPath = location.getPath().split("/");

            ResourceLocation bookId = ResourceLocation.fromNamespaceAndPath(location.getNamespace(), splitPath[0]);
            String langCode = splitPath[1];
            String type = splitPath[2];
            String id = splitPath[3];

            if(type.equals("categories")) {
                CategoryImpl.CODEC.decode(JsonOps.INSTANCE, json)
                        .resultOrPartial(e -> Modopedia.LOG.error("Could not decode book Category {}: {}", location, e))
                        .ifPresent(pair -> out.computeIfAbsent(bookId, k -> new HashMap<>())
                                .computeIfAbsent(langCode, k -> new LocalisedBookContent(new HashMap<>(), new HashMap<>()))
                                .categories().put(id, pair.getFirst()));
            }
            else if(type.equals("entries")) {
                EntryImpl.CODEC.decode(JsonOps.INSTANCE, json)
                        .resultOrPartial(e -> Modopedia.LOG.error("Could not decode book Entry {}: {}", location, e))
                        .ifPresent(pair -> out.computeIfAbsent(bookId, k -> new HashMap<>())
                                .computeIfAbsent(langCode, k -> new LocalisedBookContent(new HashMap<>(), new HashMap<>()))
                                .entries().put(id, pair.getFirst().addPages(getPages(json.getAsJsonObject().getAsJsonArray("pages")))));
            }
        });

        return out;
    }

    protected void apply(Map<ResourceLocation, Map<String, LocalisedBookContent>> booksMap, ResourceManager manager, ProfilerFiller profiler) {
        BookContentManager.get().clear();
        booksMap.forEach((id, contentMap) -> BookContentManager.get().register(id, new BookContentImpl(contentMap)));
        ModopediaApiImpl.isLoading = false;
    }

    private Page[] getPages(JsonArray array) {
        Page[] out = new Page[array.size()];
        for(int i = 0; i < array.size(); i++) {
            out[i] = new PageImpl(array.get(i).getAsJsonObject(), i);
        }
        return out;
    }

    private static Map<ResourceLocation, JsonElement> scanDirectory(ResourceManager resourceManager, String name, Gson gson) {
        Map<ResourceLocation, JsonElement> out = new HashMap<>();
        FileToIdConverter filetoidconverter = FileToIdConverter.json(name);

        for (Entry<ResourceLocation, Resource> entry : filetoidconverter.listMatchingResources(resourceManager).entrySet()) {
            ResourceLocation location = entry.getKey();

            try (Reader reader = entry.getValue().openAsReader()) {
                ResourceLocation id = filetoidconverter.fileToId(location);
                out.put(id, GsonHelper.fromJson(gson, reader, JsonElement.class));
            } catch (IllegalArgumentException | IOException | JsonParseException jsonparseexception) {
                Modopedia.LOG.error("Couldn't parse book file {}: {}", location, jsonparseexception);
            }
        }
        return out;
    }
}
