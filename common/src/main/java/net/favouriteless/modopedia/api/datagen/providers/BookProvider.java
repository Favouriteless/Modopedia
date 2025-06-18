package net.favouriteless.modopedia.api.datagen.providers;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.datagen.SimpleCodecProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.PackOutput.Target;

import java.util.concurrent.CompletableFuture;

public abstract class BookProvider extends SimpleCodecProvider<Book> {

    public BookProvider(CompletableFuture<HolderLookup.Provider> registries, PackOutput output) {
        super(output.createPathProvider(Target.DATA_PACK, "modopedia/books"), registries, Book.persistentCodec());
    }

    @Override
    public String getName() {
        return Modopedia.MOD_ID + " Books";
    }

}