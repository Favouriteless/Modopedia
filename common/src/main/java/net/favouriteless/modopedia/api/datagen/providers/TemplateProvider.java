package net.favouriteless.modopedia.api.datagen.providers;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
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

public abstract class TemplateProvider implements DataProvider {

    private final String modId;
    private final PathProvider pathProvider;
    private final CompletableFuture<Provider> registries;

    protected TemplateProvider(String modId, CompletableFuture<Provider> registries, PackOutput output) {
        this.pathProvider = output.createPathProvider(Target.RESOURCE_PACK, "modopedia/templates");
        this.registries = registries;
        this.modId = modId;
    }

    protected abstract void build(BiConsumer<ResourceLocation, JsonElement> output);

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return registries.thenCompose(registries -> run(output, registries));
    }

    private CompletableFuture<?> run(final CachedOutput output, final HolderLookup.Provider registries) {
        final Set<ResourceLocation> set = Sets.newHashSet();
        final List<CompletableFuture<?>> generated = new ArrayList<>();

        build((id, template) -> {
            if(!set.add(id))
                throw new IllegalStateException("Duplicate " + getName() + ": " + id);
            generated.add(DataProvider.saveStable(output, template, pathProvider.json(id)));
        });
        return CompletableFuture.allOf(generated.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Modopedia Templates[" + modId + "]";
    }

}