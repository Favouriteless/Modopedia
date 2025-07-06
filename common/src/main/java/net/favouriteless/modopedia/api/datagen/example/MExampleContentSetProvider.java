package net.favouriteless.modopedia.api.datagen.example;

import net.favouriteless.modopedia.api.datagen.*;
import net.favouriteless.modopedia.api.datagen.builders.*;
import net.favouriteless.modopedia.api.datagen.builders.page_components.components.*;
import net.favouriteless.modopedia.api.datagen.providers.ContentSetProvider;
import net.favouriteless.modopedia.book.text.Justify;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class MExampleContentSetProvider extends ContentSetProvider {

    public MExampleContentSetProvider(String book, String language, CompletableFuture<Provider> registries, PackOutput output) {
        super("example", book, language, registries, output);
    }

    @Override
    public void buildEntries(Provider registries, BookContentOutput output) {
        EntryBuilder.of("Text Entry")
                .icon(Items.DIAMOND.getDefaultInstance())
                .assignedItems(Items.DIAMOND)
                .page(
                        TextBuilder.of("Example Text").justify(Justify.CENTER)
                )
                .page(
                        GalleryBuilder.of(
                                TextBuilder.of("$(c:red)Gallery component 1").justify(Justify.CENTER),
                                TextBuilder.of("$(c:green)Gallery component 2").justify(Justify.CENTER),
                                TextBuilder.of("$(c:blue)Gallery component 3").justify(Justify.CENTER)
                        )
                )
                .build("text_entry", output);
    }

    @Override
    public void buildCategories(Provider registries, BookContentOutput output) {
        CategoryBuilder.of("Example Category")
                .icon(Items.STONE.getDefaultInstance())
                .entries("text_entry")
                .build("example", output);
    }

}