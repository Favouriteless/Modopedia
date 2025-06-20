package net.favouriteless.modopedia.api.datagen.example;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.BookTexture;
import net.favouriteless.modopedia.api.datagen.builders.BookTextureBuilder;
import net.favouriteless.modopedia.api.datagen.providers.BookTextureProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class MExampleBookTextureProvider extends BookTextureProvider {

    public MExampleBookTextureProvider(CompletableFuture<Provider> registries, PackOutput output) {
        super("example", registries, output);
    }

    @Override
    protected void build(Provider registries, BiConsumer<String, BookTexture> output) {
        BookTextureBuilder.of("example")
                .texture(Modopedia.id("textures/gui/books/brown_brass.png"), 290, 310)
                .sized(290, 182)
                .titleBacker(0, 12, 0, 213, 140, 35)
                .leftButton(0, 183, 0, 183, 18, 10)
                .rightButton(272, 183, 19, 183, 18, 10)
                .backButton(136, 183, 54, 183, 18, 11)
                .refreshButton(26, 159, 38, 183, 15, 14)
                .page(33, 16, 100, 150)
                .page(157, 16, 100, 150) // Fields below this point are optional
                .widget("separator", 0, 249, 100, 6)
                .widget("small_frame", 54, 255, 20, 20)
                .widget("medium_frame", 140, 182, 54, 54)
                .widget("large_frame", 140, 182, 104, 104)
                .widget("crafting_grid", 74, 255, 54, 54)
                .widget("crafting_arrow", 73, 183, 16, 13)
                .widget("crafting_flame", 89, 183, 14, 14)
                .build(output);
    }

}