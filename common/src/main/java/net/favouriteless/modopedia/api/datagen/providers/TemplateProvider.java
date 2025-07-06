package net.favouriteless.modopedia.api.datagen.providers;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;
import net.favouriteless.modopedia.api.datagen.TemplateOutput;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.PackOutput.PathProvider;
import net.minecraft.data.PackOutput.Target;
import net.minecraft.resources.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public abstract class TemplateProvider implements DataProvider {

    private final String modId;
    private final PathProvider pathProvider;
    private final CompletableFuture<Provider> registries;

    protected TemplateProvider(String modId, CompletableFuture<Provider> registries, PackOutput output) {
        this.pathProvider = output.createPathProvider(Target.RESOURCE_PACK, "modopedia/templates");
        this.registries = registries;
        this.modId = modId;
    }

    protected abstract void build(TemplateOutput output);

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return registries.thenCompose(registries -> run(output, registries));
    }

    private CompletableFuture<?> run(final CachedOutput output, final HolderLookup.Provider registries) {
        final Set<String> set = Sets.newHashSet();
        final List<CompletableFuture<?>> generated = new ArrayList<>();

        build(new TemplateOutput() {
            @Override
            public <T> RegistryOps<T> registryOps( DynamicOps<T> ops) {
                return registries.createSerializationContext(ops);
            }

            @Override
            public void accept(String id, JsonElement template) {
                if (!set.add(id))
                    throw new IllegalStateException("Duplicate " + getName() + ": " + id);
                generated.add(DataProvider.saveStable(output, template, pathProvider.json(ResourceLocation.fromNamespaceAndPath(modId, id))));
            }
        });
        return CompletableFuture.allOf(generated.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Modopedia Templates[" + modId + "]";
    }

}