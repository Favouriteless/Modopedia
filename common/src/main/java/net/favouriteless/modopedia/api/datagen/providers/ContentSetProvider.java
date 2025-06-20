package net.favouriteless.modopedia.api.datagen.providers;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import net.favouriteless.modopedia.api.book.Category;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.PackOutput.PathProvider;
import net.minecraft.data.PackOutput.Target;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class ContentSetProvider implements DataProvider {

    private final String modId;
    private final CompletableFuture<HolderLookup.Provider> registries;

    private final PathProvider entryPathProvider;
    private final PathProvider categoryPathProvider;

    private final String book;
    private final String language;

    protected ContentSetProvider(String modId, String book, String language, CompletableFuture<HolderLookup.Provider> registries, PackOutput output) {
        this.entryPathProvider = output.createPathProvider(Target.RESOURCE_PACK, "modopedia/books/" + book + "/" + language + "/entries");
        this.categoryPathProvider = output.createPathProvider(Target.RESOURCE_PACK, "modopedia/books/" + book + "/" + language + "/categories");
        this.registries = registries;
        this.book = book;
        this.language = language;
        this.modId = modId;
    }

    public abstract void buildEntries(Provider registries, BiConsumer<String, JsonElement> output);

    public abstract void buildCategories(Provider registries, BiConsumer<String, Category> output);

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return registries.thenCompose(registries -> run(output, registries));
    }

    private CompletableFuture<?> run(final CachedOutput output, final HolderLookup.Provider registries) {
        final Set<String> entries = Sets.newHashSet();
        final Set<String> categories = Sets.newHashSet();

        final List<CompletableFuture<?>> futures = new ArrayList<>();

        buildEntries(registries, (id, entry) -> {
            if(!entries.add(id))
                throw new IllegalStateException("Duplicate entry in " + getName() + " " + id);
            futures.add(DataProvider.saveStable(output, entry, entryPathProvider.json(ResourceLocation.fromNamespaceAndPath(modId, id))));
        });

        buildCategories(registries, (id, cat) -> {
            if(!categories.add(id))
                throw new IllegalStateException("Duplicate category in " + getName() + " " + id);
            futures.add(DataProvider.saveStable(output, registries, Category.codec(), cat, categoryPathProvider.json(ResourceLocation.fromNamespaceAndPath(modId, id))));
        });

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Modopedia BookContent[" + modId + "]: " + book + "[" + language + "]";
    }

}
