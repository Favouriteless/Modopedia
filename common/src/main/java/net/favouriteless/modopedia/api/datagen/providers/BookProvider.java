package net.favouriteless.modopedia.api.datagen.providers;

import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.common.datagen.SimpleCodecProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.PackOutput.Target;

import java.util.concurrent.CompletableFuture;

public abstract class BookProvider extends SimpleCodecProvider<Book> {

    public BookProvider(String modId, CompletableFuture<Provider> registries, PackOutput output) {
        super(modId, output.createPathProvider(Target.DATA_PACK, "modopedia/books"), registries, Book.persistentCodec());
    }

    @Override
    public String getName() {
        return "Modopedia Books[" + modId + "]";
    }

}