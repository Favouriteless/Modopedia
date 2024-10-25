package net.favouriteless.modopedia.client.reload_listeners;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.ModopediaApiImpl;
import net.favouriteless.modopedia.api.books.BookContentManager;
import net.favouriteless.modopedia.api.books.Page;
import net.favouriteless.modopedia.book.BookContentImpl;
import net.favouriteless.modopedia.book.BookContentImpl.LocalisedBookContent;
import net.favouriteless.modopedia.book.CategoryImpl;
import net.favouriteless.modopedia.book.EntryImpl;
import net.favouriteless.modopedia.book.PageImpl;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class BookContentReloadListener extends SimpleJsonResourceReloadListener {

    public BookContentReloadListener(String directory) {
        super(new Gson(), directory);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonMap, ResourceManager manager, ProfilerFiller profiler) {
        ModopediaApiImpl.isLoading = true;
        BookContentManager.get().clear();

        Map<ResourceLocation, Map<String, LocalisedBookContent>> books = new HashMap<>();
        jsonMap.forEach((location, element) -> {
            try {
                String[] splitPath = location.getPath().split("/");
                if(splitPath[0].equals("templates"))
                    return;

                ResourceLocation bookId = ResourceLocation.fromNamespaceAndPath(location.getNamespace(), splitPath[0]);
                String langCode = splitPath[1];
                String type = splitPath[2];
                String id = splitPath[3];

                if(type.equals("categories")) {
                    CategoryImpl.CODEC.decode(JsonOps.INSTANCE, element)
                            .resultOrPartial(e -> Modopedia.LOG.error("Error loading category {}: {}", location, e))
                            .ifPresent(p -> books.computeIfAbsent(bookId, k -> new HashMap<>())
                                    .computeIfAbsent(langCode, k -> LocalisedBookContent.create())
                                    .categories().put(id, p.getFirst()));
                }
                else if(type.equals("entries")) {
                    EntryImpl.CODEC.decode(JsonOps.INSTANCE, element)
                            .resultOrPartial(e -> Modopedia.LOG.error("Error loading entry {}: {}", location, e))
                            .ifPresent(p -> books.computeIfAbsent(bookId, k -> new HashMap<>())
                                    .computeIfAbsent(langCode, k -> LocalisedBookContent.create())
                                    .entries().put(id, p.getFirst().addPages(getPages(element.getAsJsonObject().getAsJsonArray("pages")))));
                }
            }
            catch (JsonParseException e) {
                Modopedia.LOG.error("Critical error loading book {}: {}", location, e);
            }
        });

        books.forEach((id, content) -> BookContentManager.get().register(id, new BookContentImpl(content)));
        ModopediaApiImpl.isLoading = false;
    }

    private Page[] getPages(JsonArray array) {
        Page[] out = new Page[array.size()];
        for(int i = 0; i < array.size(); i++) {
            out[i] = new PageImpl(array.get(i).getAsJsonObject(), i);
        }
        return out;
    }


}
