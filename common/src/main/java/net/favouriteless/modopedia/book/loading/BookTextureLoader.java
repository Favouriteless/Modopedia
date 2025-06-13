package net.favouriteless.modopedia.book.loading;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.registries.client.BookTextureRegistry;
import net.favouriteless.modopedia.book.registries.client.BookTextureRegistryImpl;
import net.favouriteless.modopedia.util.MExtraCodecs;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class BookTextureLoader extends JsonResourceLoader {

    public BookTextureLoader(Gson gson, String dir) {
        super(gson, dir);
    }

    @Override
    protected void load(Map<ResourceLocation, JsonElement> jsonMap) {
        BookTextureRegistryImpl.INSTANCE.clear();
        jsonMap.forEach((location, element) -> MExtraCodecs.BOOK_TEXTURE.decode(JsonOps.INSTANCE, element)
                .ifSuccess(p -> BookTextureRegistry.get().register(location, p.getFirst()))
                .ifError(e -> Modopedia.LOG.error("Error loading book texture {}: {}", location.toString(), e.message()))
        );
    }

}
