package net.favouriteless.modopedia.book.loading;

import com.google.gson.*;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.Variable;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.book.Category;
import net.favouriteless.modopedia.api.book.Page;
import net.favouriteless.modopedia.api.book.page_components.PageComponent;
import net.favouriteless.modopedia.api.registries.client.BookContentRegistry;
import net.favouriteless.modopedia.api.registries.common.BookRegistry;
import net.favouriteless.modopedia.api.registries.client.PageComponentRegistry;
import net.favouriteless.modopedia.api.registries.client.TemplateRegistry;
import net.favouriteless.modopedia.book.*;
import net.favouriteless.modopedia.book.BookContentImpl.LocalisedBookContentImpl;
import net.favouriteless.modopedia.client.page_components.GalleryPageComponent;
import net.favouriteless.modopedia.client.page_components.TemplatePageComponent;
import net.favouriteless.modopedia.book.variables.JsonVariable;
import net.favouriteless.modopedia.book.variables.RemoteVariable;
import net.favouriteless.modopedia.book.variables.VariableLookup;
import net.favouriteless.modopedia.client.ScreenCacheImpl;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * Responsible for loading the content of all books (categories, entries etc.). Do not attempt to use this server-side.
 */
public class BookContentLoader {

    public static final String BOOK_DIR = Modopedia.MOD_ID + "/books";
    public static final String TEMPLATE_DIR = Modopedia.MOD_ID + "/templates";
    public static final String BOOK_TEX_DIR = Modopedia.MOD_ID + "/book_textures";
    public static final String MULTIBLOCK_DIR = Modopedia.MOD_ID + "/multiblocks";

    private static final Gson GSON = new Gson();
    private static final TemplateLoader templateLoader = new TemplateLoader(GSON, TEMPLATE_DIR);
    private static final BookTextureLoader textureLoader = new BookTextureLoader(GSON, BOOK_TEX_DIR);
    private static final MultiblockLoader multiblockLoader = new MultiblockLoader(GSON, MULTIBLOCK_DIR);

    public static void reloadAll() {
        ScreenCacheImpl.INSTANCE.clear();
        reload((manager, level) -> BookRegistry.get().getBookIds().forEach(id -> reloadBook(id, manager, level)));
    }

    public static void reload(ResourceLocation id) {
        ScreenCacheImpl.INSTANCE.remove(id);
        reload((manager, level) -> reloadBook(id, manager, level));
    }

    private static void reload(BiConsumer<ResourceManager, Level> reloader) {
        Minecraft mc = Minecraft.getInstance();
        ResourceManager rm = mc.getResourceManager();
        if(mc.level != null)
            preReload(rm).thenRun(() -> reloader.accept(rm, mc.level));
    }


    private static CompletableFuture<Void> preReload(ResourceManager manager) {
        return CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> templateLoader.reload(manager).thenRun(() -> Modopedia.LOG.info("Reloaded templates")), Util.backgroundExecutor()),
                CompletableFuture.runAsync(() -> textureLoader.reload(manager).thenRun(() -> Modopedia.LOG.info("Reloaded book textures")), Util.backgroundExecutor()),
                CompletableFuture.runAsync(() -> multiblockLoader.reload(manager).thenRun(() -> Modopedia.LOG.info("Reloaded multiblocks")), Util.backgroundExecutor())
        );
    }

    private static CompletableFuture<Void> reloadBook(ResourceLocation id, ResourceManager manager, Level level) {
        Book book = BookRegistry.get().getBook(id);
        if(book == null)
            return CompletableFuture.completedFuture(null);

        return CompletableFuture
                .supplyAsync(() -> getBookResources(id, manager), Util.backgroundExecutor())
                .thenApplyAsync(map -> parseBookResources(map, book, level), Util.backgroundExecutor())
                .thenAccept(content -> {
                    BookContentRegistry.get().register(id, new BookContentImpl(content));
                    Modopedia.LOG.info("Reloaded book: {}", id);
                });
    }

    private static Map<ResourceLocation, JsonElement> getBookResources(ResourceLocation bookId, ResourceManager manager) {
        Map<ResourceLocation, JsonElement> out = new HashMap<>();

        FileToIdConverter converter = FileToIdConverter.json(BOOK_DIR + "/" + bookId.getPath());
        for(Entry<ResourceLocation, Resource> entry : converter.listMatchingResources(manager).entrySet()) {
            try (Reader reader = entry.getValue().openAsReader()) {
                out.put(converter.fileToId(entry.getKey()), GsonHelper.fromJson(GSON, reader, JsonElement.class));
            } catch (IllegalArgumentException | IOException | JsonParseException exception) {
                Modopedia.LOG.error("Error trying to fetch resource {}: {}", entry.getKey(), exception);
            }
        }
        return out;
    }

    private static Map<String, LocalisedBookContent> parseBookResources(Map<ResourceLocation, JsonElement> jsonMap,
                                                                        Book book, Level level) {
        Map<String, LocalisedBookContentImpl> content = new HashMap<>();
        for(Entry<ResourceLocation, JsonElement> entry : jsonMap.entrySet()) {
            String[] splitPath = entry.getKey().getPath().split("/", 3);
            if(splitPath.length < 3) {
                Modopedia.LOG.error("Invalid book resource found: {}", entry.getKey());
                continue;
            }
            String langCode = splitPath[0];
            String type = splitPath[1];
            String id = splitPath[2];

            if(type.equals("categories"))
                loadCategory(entry.getValue(), book, id, level, content.computeIfAbsent(langCode, k -> LocalisedBookContentImpl.create()).categories());
            else if(type.equals("entries"))
                loadEntry(entry.getValue(), book, id, level, content.computeIfAbsent(langCode, k -> LocalisedBookContentImpl.create()).entries());
        }
        return new HashMap<>(content); // Convert map to the "immutable" LocalisedBookContent type after loading.
    }

    private static void loadEntry(JsonElement json, Book book, String id, Level level,
                                  Map<String, net.favouriteless.modopedia.api.book.Entry> entries) {
        EntryImpl.CODEC.decode(RegistryOps.create(JsonOps.INSTANCE, level.registryAccess()), json)
                .ifSuccess(p -> entries.put(id, ((EntryImpl)p.getFirst()).addPages(loadPages(id, json.getAsJsonObject().getAsJsonArray("pages"), book, level))))
                .ifError(e -> Modopedia.LOG.error("Error loading entry json {}: {}", id, e.message()));
    }

    private static void loadCategory(JsonElement json, Book book, String id, Level level,
                                     Map<String, Category> categories) {
        CategoryImpl.CODEC.decode(RegistryOps.create(JsonOps.INSTANCE, level.registryAccess()), json)
                .ifSuccess(p -> categories.put(id, ((CategoryImpl)p.getFirst()).init(book))) // Init just parses the landing text
                .ifError(e -> Modopedia.LOG.error("Error loading entry {}: {}", id, e.message()));
    }

    /**
     * Create pages from a given JsonArray and run init on their components.
     */
    private static List<Page> loadPages(String entry, JsonArray array, Book book, Level level) {
        List<Page> out = new ArrayList<>();

        for(JsonElement element : array) {
            try {
                PageImpl page = new PageImpl(loadPageComponentHolder(entry, element.getAsJsonObject(), out.size()));
                page.init(book, entry, level);
                out.add(page);
            }
            catch(Exception e) {
                Modopedia.LOG.error("Error loading page for entry {}: {}", entry, e.getMessage());
            }
        }
        return out;
    }

    /**
     * Create a new PageComponentHolder and set up it's variables/components from the given JsonObject.
     */
    private static PageComponentHolder loadPageComponentHolder(String entry, JsonObject json, int pageNum) {
        PageComponentHolder holder = new PageComponentHolder();

        holder.set("page_num", Variable.of(pageNum));
        holder.set("entry", Variable.of(entry));

        json.keySet().forEach(key -> {
            if(!key.equals("components"))
                holder.set(key, JsonVariable.of(json.get(key)));
        });

        if(!json.has("components"))
            return holder;

        JsonArray components = json.getAsJsonArray("components");
        if(components.isEmpty())
            return holder;

        for(JsonElement element : components) {
            if(element.isJsonObject())
                loadComponent(entry, holder, element.getAsJsonObject(), pageNum);
            else
                Modopedia.LOG.error("Invalid component found in {}", entry);
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
    private static void loadComponent(String entry, PageComponentHolder holder, JsonObject json, int pageNum) {
        PageComponent component;
         if(json.has("type")) {
            ResourceLocation id = ResourceLocation.parse(json.get("type").getAsString());
            Supplier<PageComponent> type = PageComponentRegistry.get().get(id);

            if(type == null)
                throw new JsonParseException(String.format("Error creating PageComponent: %s is not a registered component type", id));

            component = type.get();
        }
        else if(json.has("template")) {
            ResourceLocation id = ResourceLocation.parse(json.get("template").getAsString());
            Template template = TemplateRegistry.get().getTemplate(id);
            if(template == null)
                throw new JsonParseException(String.format("Error creating PageComponent: %s is not a registered template", id));
            component = new TemplatePageComponent(loadPageComponentHolder(entry, template.getData(), pageNum));
        }
        else if(json.has("components")) {
            component = new GalleryPageComponent(loadPageComponentHolder(entry, json, pageNum));
        }
        else {
            throw new JsonParseException("Error creating PageComponent: component does not have a type, template or gallery");
        }

        VariableLookup lookup = new VariableLookup();
        lookup.set("page_num", Variable.of(pageNum));
        lookup.set("entry", Variable.of(entry));

        for(String key : json.keySet()) {
            if(key.equals("type"))
                continue;

            JsonElement element = json.get(key);

            if(element instanceof JsonPrimitive primitive && primitive.isString()) { // Handle passthrough variables
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

}
