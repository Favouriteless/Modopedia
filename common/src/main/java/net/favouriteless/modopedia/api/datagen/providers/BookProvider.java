package net.favouriteless.modopedia.api.datagen.providers;

import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.datagen.SimpleCodecProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.PackOutput.Target;

import java.util.concurrent.CompletableFuture;

public abstract class BookProvider extends SimpleCodecProvider<Book> {

    private final String modId;

    public BookProvider(String modId, CompletableFuture<Provider> registries, PackOutput output) {
        super(output.createPathProvider(Target.DATA_PACK, "modopedia/books"), registries, Book.persistentCodec());
        this.modId = modId;
    }

    @Override
    public String getName() {
        return "Modopedia Books[" + modId + "]";
    }

}