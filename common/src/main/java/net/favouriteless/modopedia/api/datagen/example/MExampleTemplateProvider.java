package net.favouriteless.modopedia.api.datagen.example;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.datagen.BookContentOutput;
import net.favouriteless.modopedia.api.datagen.builders.TemplateBuilder;
import net.favouriteless.modopedia.api.datagen.builders.page_components.components.TextBuilder;
import net.favouriteless.modopedia.api.datagen.providers.TemplateProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class MExampleTemplateProvider extends TemplateProvider {

    public MExampleTemplateProvider(CompletableFuture<Provider> registries, PackOutput output) {
        super("example", registries, output);
    }

    @Override
    protected void build(Provider registries, BookContentOutput output) {
        TemplateBuilder.of()
                .processor(Modopedia.id("example"))
                .components(
                        TextBuilder.of("example text")
                                .width("#width")
                                .lineHeight(9)
                )
                .defaultValue("width", 100)
                .build("example", output);
    }

}