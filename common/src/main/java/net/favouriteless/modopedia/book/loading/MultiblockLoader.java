package net.favouriteless.modopedia.book.loading;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.multiblock.Multiblock;
import net.favouriteless.modopedia.api.registries.client.MultiblockRegistry;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class MultiblockLoader extends JsonResourceLoader {

    public MultiblockLoader(Gson gson, String dir) {
        super(gson, dir);
    }

    @Override
    protected void load(Map<ResourceLocation, JsonElement> jsonMap) {
        jsonMap.forEach((location, element) -> Multiblock.codec().decode(JsonOps.INSTANCE, element)
                .ifSuccess(p -> MultiblockRegistry.get().register(location, p.getFirst()))
                .ifError(e -> Modopedia.LOG.error("Error loading multiblock {}: {}", location.toString(), e.message()))
        );
    }

}
