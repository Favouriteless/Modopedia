package net.favouriteless.modopedia.book;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.BookRegistry;
import net.favouriteless.modopedia.api.books.BookContentManager;
import net.favouriteless.modopedia.api.books.Page;
import net.favouriteless.modopedia.book.BookContentImpl.LocalisedBookContent;
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

public class BookContentLoader {

    private static final Gson gson = new Gson();

    public static void reloadAllBookContent(ResourceManager manager) {
        for(ResourceLocation id : BookRegistry.get().getBookIds()) {
            reloadBookContent(id, manager);
        }
    }

    public static void reloadBookContent(ResourceLocation id, ResourceManager manager) {
        CompletableFuture
                .supplyAsync(() -> getBookResources(id, manager), Util.backgroundExecutor())
                .thenApplyAsync(map -> parseBookResources(map, id), Util.backgroundExecutor())
                .thenAcceptAsync(map -> BookContentManager.get().register(id, new BookContentImpl(map)), Minecraft.getInstance());
    }

    private static Map<ResourceLocation, JsonElement> getBookResources(ResourceLocation bookId, ResourceManager manager) {
        FileToIdConverter filetoidconverter = FileToIdConverter.json(Modopedia.BOOK_DIRECTORY + "/" + bookId.getPath());

        Map<ResourceLocation, JsonElement> out = new HashMap<>();
        for(Entry<ResourceLocation, Resource> entry : filetoidconverter.listMatchingResources(manager).entrySet()) {
            try (Reader reader = entry.getValue().openAsReader()) {
                JsonElement json = GsonHelper.fromJson(gson, reader, JsonElement.class);
                ResourceLocation nonFile = filetoidconverter.fileToId(entry.getKey());
                out.put(nonFile, json);
            } catch (IllegalArgumentException | IOException | JsonParseException exception) {
                Modopedia.LOG.error("Error trying to fetch book resources for {}: {}", entry.getKey(), exception);
            }
        }

        return out;
    }

    private static Map<String, LocalisedBookContent> parseBookResources(Map<ResourceLocation, JsonElement> jsonMap, ResourceLocation bookId) {
        Map<String, LocalisedBookContent> content = new HashMap<>();
        jsonMap.forEach((location, element) -> {
            String[] splitPath = location.getPath().split("/");
            String langCode = splitPath[0];
            String type = splitPath[1];
            String id = splitPath[2];

            loadBookJson(element, bookId, id, type, content.computeIfAbsent(langCode, k -> LocalisedBookContent.create()));
        });
        return content;
    }

    private static void loadBookJson(JsonElement json, ResourceLocation location, String id, String type, LocalisedBookContent content) {
        try {
            if(type.equals("categories")) {
                CategoryImpl.CODEC.decode(JsonOps.INSTANCE, json)
                        .resultOrPartial(e -> Modopedia.LOG.error("Error loading category {}: {}", id, e))
                        .ifPresent(p -> content.categories().put(id, p.getFirst()));
            }
            else if(type.equals("entries")) {
                EntryImpl.CODEC.decode(JsonOps.INSTANCE, json)
                        .resultOrPartial(e -> Modopedia.LOG.error("Error loading entry {}: {}", id, e))
                        .ifPresent(p -> content.entries().put(id, p.getFirst().addPages(getPages(json.getAsJsonObject().getAsJsonArray("pages")))));
            }
        }
        catch(Exception e) {
            Modopedia.LOG.error("Error loading book json {}: {}", id, e);
        }
    }

    private static Page[] getPages(JsonArray array) {
        Page[] out = new Page[array.size()];
        for(int i = 0; i < array.size(); i++) {
            out[i] = new PageImpl(array.get(i).getAsJsonObject(), i);
        }
        return out;
    }

}
