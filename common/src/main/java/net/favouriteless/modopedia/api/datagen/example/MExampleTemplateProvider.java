package net.favouriteless.modopedia.api.datagen.example;

import com.google.gson.JsonElement;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.datagen.providers.TemplateProvider;
import net.favouriteless.modopedia.api.datagen.builders.TemplateBuilder;
import net.favouriteless.modopedia.api.datagen.builders.page_components.TextPageComponentBuilder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class MExampleTemplateProvider extends TemplateProvider {

    public MExampleTemplateProvider(CompletableFuture<Provider> registries, PackOutput output) {
        super("example", registries, output);
    }

    @Override
    protected void build(BiConsumer<ResourceLocation, JsonElement> output) {
        TemplateBuilder.of(Modopedia.id("example"))
                .processor(Modopedia.id("example"))
                .components(
                        TextPageComponentBuilder.of("example text")
                                .width("#width")
                                .lineHeight(9)
                )
                .defaultValue("width", 100)
                .build(output);
    }

}