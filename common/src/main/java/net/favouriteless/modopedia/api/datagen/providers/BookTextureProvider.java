package net.favouriteless.modopedia.api.datagen.providers;

import net.favouriteless.modopedia.api.book.BookTexture;
import net.favouriteless.modopedia.datagen.SimpleCodecProvider;
import net.favouriteless.modopedia.util.common.MExtraCodecs;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.PackOutput.Target;

import java.util.concurrent.CompletableFuture;

public abstract class BookTextureProvider extends SimpleCodecProvider<BookTexture> {

    public BookTextureProvider(String modId, CompletableFuture<Provider> registries, PackOutput output) {
        super(modId, output.createPathProvider(Target.RESOURCE_PACK, "modopedia/book_textures"), registries, MExtraCodecs.BOOK_TEXTURE);
    }

    @Override
    public String getName() {
        return "Modopedia BookTextures[" + modId + "]";
    }

}
