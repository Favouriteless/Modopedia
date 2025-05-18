package net.favouriteless.modopedia.book.registries;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.registries.BookTextureRegistry;
import net.favouriteless.modopedia.api.books.BookTexture;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class BookTextureRegistryImpl implements BookTextureRegistry {

    public static final BookTextureRegistryImpl INSTANCE = new BookTextureRegistryImpl();

    private final Map<ResourceLocation, BookTexture> textures = new HashMap<>();

    private BookTextureRegistryImpl() {}

    @Override
    public void register(ResourceLocation id, BookTexture texture) {
        if(textures.containsKey(id))
            Modopedia.LOG.error("Attempted to register duplicate book texture: {}", id);
        else
            textures.put(id, texture);
    }

    @Override
    public BookTexture getTexture(ResourceLocation id) {
        return textures.get(id);
    }

    public void clear() {
        textures.clear();
    }

}
