package net.favouriteless.modopedia.api.datagen.example;

import com.google.gson.JsonElement;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.datagen.builders.TemplateBuilder;
import net.favouriteless.modopedia.api.datagen.builders.page_components.TextComponentBuilder;
import net.favouriteless.modopedia.api.datagen.providers.TemplateProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class MExampleTemplateProvider extends TemplateProvider {

    public MExampleTemplateProvider(CompletableFuture<Provider> registries, PackOutput output) {
        super("example", registries, output);
    }

    @Override
    protected void build(BiConsumer<String, JsonElement> output) {
        TemplateBuilder.of("example")
                .processor(Modopedia.id("example"))
                .components(
                        TextComponentBuilder.of("example text")
                                .width("#width")
                                .lineHeight(9)
                )
                .defaultValue("width", 100)
                .build(output);
    }

}