package net.favouriteless.modopedia.api.datagen.example;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.datagen.builders.BookBuilder;
import net.favouriteless.modopedia.api.datagen.providers.BookProvider;
import net.favouriteless.modopedia.common.book_types.ClassicBookType;
import net.favouriteless.modopedia.common.book_types.LockedViewType;
import net.favouriteless.modopedia.common.init.MSoundEvents;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class MExampleBookProvider extends BookProvider {

    public MExampleBookProvider(CompletableFuture<Provider> registries, PackOutput output) {
        super("example", registries, output);
    }

    @Override
    protected void build(Provider registries, BiConsumer<String, Book> output) {
        BookBuilder.of("Datagen Example") // All fields except title are optional.
                .subtitle("Example Subtitle")
                .type(new ClassicBookType(LockedViewType.TRANSLUCENT))
                .landingText("Example $(b)Landing$() $(c:green)Text$()!")
                .texture(Modopedia.id("brown_brass"))
                .itemModel(Modopedia.id("item/modopedia_books/purple_gold"))
                .tab(ResourceLocation.withDefaultNamespace("combat"))
                .openSound(MSoundEvents.BOOK_OPEN)
                .flipSound(MSoundEvents.BOOK_FLIP)
                .font(Modopedia.id("default"))
                .textColour(0x000000)
                .headerColour(0x000000)
                .lineWidth(100)
                .build("datagen_example", output);
    }

}