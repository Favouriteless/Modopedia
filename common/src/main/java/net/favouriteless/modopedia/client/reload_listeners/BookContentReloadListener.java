package net.favouriteless.modopedia.client.reload_listeners;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.ModopediaApiImpl;
import net.favouriteless.modopedia.api.books.BookContent;
import net.favouriteless.modopedia.api.books.BookContentManager;
import net.favouriteless.modopedia.api.books.Category;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.book.BookContentImpl;
import net.favouriteless.modopedia.book.BookContentImpl.LocalisedBookContent;
import net.favouriteless.modopedia.client.reload_listeners.BookContentReloadListener.BookJsonData;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookContentReloadListener extends SimplePreparableReloadListener<Map<ResourceLocation, Map<String, BookJsonData>>> {

    private static final Gson GSON = new Gson();
    private static final List<String> VALID_TYPES = List.of("entries", "categories");
    private final String directory;

    public BookContentReloadListener(String directory) {
        this.directory = directory;
    }

    @Override
    protected Map<ResourceLocation, Map<String, BookJsonData>> prepare(ResourceManager manager, ProfilerFiller profiler) {
        FileToIdConverter filetoidconverter = FileToIdConverter.json(directory);
        Map<ResourceLocation, Map<String, BookJsonData>> output = new HashMap<>();

        for(Map.Entry<ResourceLocation, Resource> entry : filetoidconverter.listMatchingResources(manager).entrySet()) {
            // Strip off the book folder. Should be book id -> lang code -> subdir -> json
            String[] splitPath = entry.getKey().getPath().substring(directory.length()+1).split("/");
            if(splitPath.length != 4) {
                Modopedia.LOG.error("Illegal book resource location, skipping: {}", entry.getKey());
                break;
            }

            ResourceLocation bookId = ResourceLocation.fromNamespaceAndPath(entry.getKey().getNamespace(), splitPath[0]);
            String langCode = splitPath[1];
            String type = splitPath[2];
            String id = splitPath[3].substring(0, splitPath[3].length()-5); // path without .json extension

            if(!VALID_TYPES.contains(type)) {
                Modopedia.LOG.error("Illegal book resource type, skipping: {}", entry.getKey());
                break;
            }

            try(Reader reader = entry.getValue().openAsReader()) {
                JsonElement jsonElement = GsonHelper.fromJson(GSON, reader, JsonElement.class);

                BookJsonData data = output.computeIfAbsent(bookId, k -> new HashMap<>())
                        .computeIfAbsent(langCode, k -> new BookJsonData(new HashMap<>(), new HashMap<>()));

                if(type.equals("categories")) // TODO: Come up with a way to NOT do this. Entire function inefficient.
                    data.categories.put(id, jsonElement);
                else if(type.equals("entries"))
                    data.entries.put(id, jsonElement);

            } catch (IllegalArgumentException | IOException | JsonParseException exception) {
                Modopedia.LOG.error("Error loading book contents {}: {}", bookId, entry.getKey(), exception);
            }
        }

        return output;
    }

    @Override
    protected void apply(Map<ResourceLocation, Map<String, BookJsonData>> bookMap, ResourceManager manager, ProfilerFiller profiler) {
        ModopediaApiImpl.isLoading = true;
        BookContentManager.get().clear();

        bookMap.forEach((id, data) -> {
            try {
                BookContent content = parseBookContent(id, data);
                BookContentManager.get().register(id, content);
            }
            catch(IllegalArgumentException | JsonParseException exception) {
                Modopedia.LOG.error("Critical error while loading book {}: {}", id, exception.getMessage());
            }
        });
        ModopediaApiImpl.isLoading = false;
    }

    private BookContent parseBookContent(ResourceLocation id, Map<String, BookJsonData> fullData) {
        Map<String, LocalisedBookContent> contentMap = new HashMap<>();

        for(String lang : fullData.keySet()) {
            BookJsonData data = fullData.get(lang);

            Map<String, Category> categories = new HashMap<>();
            Map<String, Entry> entries = new HashMap<>();

            for(Map.Entry<String, JsonElement> category : data.categories.entrySet()) {
                Category.persistentCodec().decode(JsonOps.INSTANCE, category.getValue())
                        .resultOrPartial(error -> Modopedia.LOG.error("Error attempting to load category for {}/{}: {}", lang, id, error))
                        .ifPresent(result -> categories.put(category.getKey(), result.getFirst()));
            }
            for(Map.Entry<String, JsonElement> entry : data.entries.entrySet()) {
                Entry.persistentCodec().decode(JsonOps.INSTANCE, entry.getValue())
                        .resultOrPartial(error -> Modopedia.LOG.error("Error attempting to load entry for {}/{}: {}", lang, id, error))
                        .ifPresent(result -> entries.put(entry.getKey(), result.getFirst()));
            }

            contentMap.put(lang, new LocalisedBookContent(categories, entries));
        }

        return new BookContentImpl(contentMap);
    }

    public record BookJsonData(Map<String, JsonElement> categories,
                               Map<String, JsonElement> entries
    ) {}
}
