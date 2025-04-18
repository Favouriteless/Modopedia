package net.favouriteless.modopedia.client;

import com.google.gson.*;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.*;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.books.BookTexture;
import net.favouriteless.modopedia.api.books.Category;
import net.favouriteless.modopedia.api.books.Page;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.book.*;
import net.favouriteless.modopedia.book.BookContentImpl.LocalisedBookContentImpl;
import net.favouriteless.modopedia.book.page_components.TemplatePageComponent;
import net.favouriteless.modopedia.book.variables.JsonVariable;
import net.favouriteless.modopedia.book.variables.RemoteVariable;
import net.favouriteless.modopedia.book.variables.VariableLookup;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.Level;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * Responsible for loading the content of all books (categories, entries etc.). Do not attempt to use this on server side.
 */
public class BookContentLoader {

    private static final Gson gson = new Gson();
    
    public static final String BOOK_DIR = "books";
    public static final String TEMPLATE_DIR = "templates";
    public static final String BOOK_TEX_DIR = "book_textures";

    public static void reloadAll() {
        Minecraft mc = Minecraft.getInstance();
        ResourceManager manager = mc.getResourceManager();

        if(mc.level != null)
            reloadTemplates(manager)
                    .thenRun(() -> reloadBookTextures(manager))
                    .thenRun(() -> BookRegistry.get().getBookIds().forEach(id -> reloadInternal(id, manager, mc.level)));
    }

    public static void reload(ResourceLocation id) {
        Minecraft mc = Minecraft.getInstance();
        ResourceManager manager = mc.getResourceManager();

        if(mc.level != null)
            reloadTemplates(manager)
                    .thenRun(() -> reloadBookTextures(manager))
                    .thenRun(() -> reloadInternal(id, manager, mc.level));
    }

    private static void reloadInternal(ResourceLocation id, ResourceManager manager, Level level) {
        Book book = BookRegistry.get().getBook(id);

        if(book != null) { // If book is null we don't really want to load the content anyway.
            CompletableFuture
                    .supplyAsync(() -> getBookResources(id, manager), Util.backgroundExecutor())
                    .thenApply(map -> parseBookResources(map, book, level))
                    .thenAcceptAsync(content -> BookContentManager.get().register(id, new BookContentImpl(content)), Minecraft.getInstance())
                    .thenRunAsync(() -> Modopedia.LOG.info("Reloaded book: {}", id), Minecraft.getInstance());
        }
    }

    private static CompletableFuture<Void> reloadTemplates(ResourceManager manager) {
        return CompletableFuture
                .supplyAsync(() -> getTemplateResources(manager), Util.backgroundExecutor())
                .thenAcceptAsync(BookContentLoader::loadTemplates, Minecraft.getInstance());
    }

    private static CompletableFuture<Void> reloadBookTextures(ResourceManager manager) {
        return CompletableFuture
                .supplyAsync(() -> getBookTextureResources(manager), Util.backgroundExecutor())
                .thenAcceptAsync(BookContentLoader::loadBookTextures, Minecraft.getInstance());
    }

    private static Map<ResourceLocation, JsonElement> getBookResources(ResourceLocation bookId, ResourceManager manager) {
        FileToIdConverter filetoidconverter = FileToIdConverter.json(String.format("%s/%s/%s", Modopedia.MOD_ID, BOOK_DIR, bookId.getPath()));

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

    private static Map<String, LocalisedBookContent> parseBookResources(Map<ResourceLocation, JsonElement> jsonMap,
                                                                        Book book, Level level) {
        Map<String, LocalisedBookContentImpl> content = new HashMap<>();
        jsonMap.forEach((location, element) -> {
            String[] splitPath = location.getPath().split("/");
            String langCode = splitPath[0];
            String type = splitPath[1];
            String id = splitPath[2];

            if(type.equals("categories"))
                loadCategoryJson(element, book, id, level, content.computeIfAbsent(langCode, k -> LocalisedBookContentImpl.create()).categories());
            else if(type.equals("entries"))
                loadEntryJson(element, book, id, level, content.computeIfAbsent(langCode, k -> LocalisedBookContentImpl.create()).entries());
        });
        return new HashMap<>(content); // Convert map to the "immutable" LocalisedBookContent type after loading.
    }

    private static void loadEntryJson(JsonElement json, Book book, String id, Level level,
                                      Map<String, net.favouriteless.modopedia.api.books.Entry> entries) {
        try {
            EntryImpl.CODEC.decode(RegistryOps.create(JsonOps.INSTANCE, level.registryAccess()), json)
                    .resultOrPartial(e -> Modopedia.LOG.error("Error loading entry {}: {}", id, e))
                    .ifPresent(p -> entries.put(id, p.getFirst()
                            .addPages(loadPages(json.getAsJsonObject().getAsJsonArray("pages"), book, level))));
        }
        catch(Exception e) {
            Modopedia.LOG.error("Error loading entry json {}: {}", id, e.getMessage());
        }
    }

    private static void loadCategoryJson(JsonElement json, Book book, String id, Level level,
                                         Map<String, Category> categories) {
        try {
            CategoryImpl.CODEC.decode(RegistryOps.create(JsonOps.INSTANCE, level.registryAccess()), json)
                    .resultOrPartial(e -> Modopedia.LOG.error("Error loading category {}: {}", id, e))
                    .ifPresent(p -> categories.put(id, p.getFirst().init(book)));
        }
        catch(Exception e) {
            Modopedia.LOG.error("Error loading category json {}: {}", id, e.getMessage());
        }
    }

    /**
     * Create pages from a given JsonArray and run init on their components.
     */
    private static Page[] loadPages(JsonArray array, Book book, Level level) {
        Page[] out = new Page[array.size()];
        for(int i = 0; i < out.length; i++) {
            JsonElement element = array.get(i);
            if(!element.isJsonObject())
                continue;

            out[i] = new PageImpl(loadPageComponentHolder(element.getAsJsonObject(), i));
            out[i].init(book, level);
        }
        return out;
    }

    /**
     * Create a new PageComponentHolder and set up it's variables/components from the given JsonObject.
     */
    private static PageComponentHolder loadPageComponentHolder(JsonObject json, int pageNum) {
        PageComponentHolder holder = new PageComponentHolder();

        json.keySet().forEach(key -> {
            if(!key.equals("components"))
                holder.set(key, JsonVariable.of(json.get(key)));
        });
        holder.set("page_num", Variable.of(pageNum));

        if(!json.has("components"))
            return holder;

        for(JsonElement element : json.getAsJsonArray("components")) {
            if(element.isJsonObject()) {
                loadComponent(holder, element.getAsJsonObject(), pageNum);
            }
        }
        return holder;
    }

    /**
     * Create a PageComponent and it's lookup from a given JsonObject.
     *
     * @param holder Parent holder to fetch remote values from and add component to.
     * @param json JsonObject to be loaded.
     * @param pageNum Index of the page this component is on.
     *
     */
    private static void loadComponent(PageComponentHolder holder, JsonObject json, int pageNum) {
        PageComponent component;
        if(json.has("type")) {
            ResourceLocation id = ResourceLocation.parse(json.get("type").getAsString());
            Supplier<PageComponent> type = PageComponentRegistry.get().get(id);

            if(type == null)
                throw new JsonParseException(String.format("Error creating PageComponent: %s is not a registered component type", id));

            component = type.get();
        }
        else if(json.has("template")) {
            component = new TemplatePageComponent(loadPageComponentHolder(TemplateRegistry.getTemplate(ResourceLocation.parse(json.get("template").getAsString())).getData(), pageNum));
        }
        else {
            throw new JsonParseException("Error creating PageComponent: component does not have a type or template");
        }

        VariableLookup lookup = new VariableLookup();
        lookup.set("page_num", Variable.of(pageNum));

        for(String key : json.keySet()) {
            if(key.equals("type"))
                continue;

            JsonElement element = json.get(key);

            if(element instanceof JsonPrimitive primitive && primitive.isString()) {
                String val = primitive.getAsString();
                if(val.startsWith("#")) {
                    lookup.set(key, RemoteVariable.of(val.substring(1), holder));
                    continue;
                }
            }
            lookup.set(key, JsonVariable.of(element));
        }
        holder.addComponent(component, lookup);
    }

    private static Map<ResourceLocation, JsonElement> getTemplateResources(ResourceManager manager) {
        FileToIdConverter filetoidconverter = FileToIdConverter.json(Modopedia.MOD_ID + "/" + TEMPLATE_DIR);

        Map<ResourceLocation, JsonElement> out = new HashMap<>();
        for(Entry<ResourceLocation, Resource> entry : filetoidconverter.listMatchingResources(manager).entrySet()) {
            try (Reader reader = entry.getValue().openAsReader()) {
                out.put(filetoidconverter.fileToId(entry.getKey()), GsonHelper.fromJson(gson, reader, JsonElement.class));
            } catch (IllegalArgumentException | IOException | JsonParseException exception) {
                Modopedia.LOG.error("Error trying to fetch template resources for {}: {}", entry.getKey(), exception);
            }
        }
        return out;
    }

    private static void loadTemplates(Map<ResourceLocation, JsonElement> jsonMap) {
        TemplateRegistry.clear();
        jsonMap.forEach((location, element) -> {
            try {
                TemplateRegistry.createTemplate(location, element.getAsJsonObject());
            }
            catch (JsonParseException e) {
                Modopedia.LOG.error("Could not load template {}: {}", location, e);
            }
        });
    }

    private static Map<ResourceLocation, JsonElement> getBookTextureResources(ResourceManager manager) {
        FileToIdConverter filetoidconverter = FileToIdConverter.json(Modopedia.MOD_ID + "/" + BOOK_TEX_DIR);

        Map<ResourceLocation, JsonElement> out = new HashMap<>();
        for(Entry<ResourceLocation, Resource> entry : filetoidconverter.listMatchingResources(manager).entrySet()) {
            try (Reader reader = entry.getValue().openAsReader()) {
                out.put(filetoidconverter.fileToId(entry.getKey()), GsonHelper.fromJson(gson, reader, JsonElement.class));
            } catch (IllegalArgumentException | IOException | JsonParseException exception) {
                Modopedia.LOG.error("Error trying to fetch book texture resources for {}: {}", entry.getKey(), exception);
            }
        }
        return out;
    }

    private static void loadBookTextures(Map<ResourceLocation, JsonElement> jsonMap) {
        BookTextureRegistry.get().clear();
        jsonMap.forEach((location, element) -> {
            try {
                BookTextureRegistry.get().register(location, BookTexture.CODEC.decode(JsonOps.INSTANCE, element).getOrThrow().getFirst());
            }
            catch(Exception e) {
                Modopedia.LOG.error("Error loading book texture {}: {}", location.toString(), e.getMessage());
            }
        });
    }

}
