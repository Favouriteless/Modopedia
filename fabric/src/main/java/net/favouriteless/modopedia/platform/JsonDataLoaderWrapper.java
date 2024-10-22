package net.favouriteless.modopedia.platform;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class JsonDataLoaderWrapper implements IdentifiableResourceReloadListener {

    private final ResourceLocation id;
    private final PreparableReloadListener reloadListener;

    public JsonDataLoaderWrapper(ResourceLocation id, PreparableReloadListener reloadListener) {
        this.id = id;
        this.reloadListener = reloadListener;
    }

    @Override
    public ResourceLocation getFabricId() {
        return id;
    }

    @Override
    public CompletableFuture<Void> reload(PreparationBarrier synchronizer, ResourceManager manager, ProfilerFiller prepareProfiler, ProfilerFiller applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
        return reloadListener.reload(synchronizer, manager, prepareProfiler, applyProfiler, prepareExecutor, applyExecutor);
    }

}