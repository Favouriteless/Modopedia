package net.favouriteless.modopedia.neoforge.datagen.providers;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.datagen.builders.TemplateBuilder;
import net.favouriteless.modopedia.api.datagen.builders.page_components.ItemBuilder;
import net.favouriteless.modopedia.api.datagen.builders.page_components.SmallFrameBuilder;
import net.favouriteless.modopedia.api.datagen.providers.TemplateProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class MTemplateProvider extends TemplateProvider {

    public MTemplateProvider(CompletableFuture<Provider> registries, PackOutput output) {
        super(Modopedia.MOD_ID, registries, output);
    }

    @Override
    protected void build(BiConsumer<String, JsonElement> output) {
        TemplateBuilder.of("framed_item")
                .processor(Modopedia.id("small_frame_spacing"))
                .components(
                        SmallFrameBuilder.of()
                                .x("#frame_x")
                                .y("#frame_y"),
                        ItemBuilder.of("#items")
                )
                .defaultValue("width", () -> new JsonPrimitive(16))
                .build(output);
    }

}
