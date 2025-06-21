package net.favouriteless.modopedia.neoforge.datagen.providers;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.BookTexture;
import net.favouriteless.modopedia.api.datagen.builders.BookTextureBuilder;
import net.favouriteless.modopedia.api.datagen.providers.BookTextureProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class MBookTextureProvider extends BookTextureProvider {

    public static final String[] METALS = { "brass", "copper", "gold", "iron", "silver" };
    public static final String[] COLOURS = { "brown", "red", "green", "blue", "purple" };

    public MBookTextureProvider(CompletableFuture<Provider> registries, PackOutput output) {
        super(Modopedia.MOD_ID, registries, output);
    }

    @Override
    protected void build(Provider registries, BiConsumer<String, BookTexture> output) {
        for(String colour : COLOURS) {
            for(String metal : METALS) {
                String loc = colour + "_" + metal;

                BookTextureBuilder.of(loc)
                        .texture(Modopedia.id("textures/gui/books/" + loc + ".png"), 290, 310)
                        .sized(290, 182)
                        .titleBacker(0, 12, 0, 213, 140, 35)
                        .leftButton(0, 183, 0, 183, 18, 10)
                        .rightButton(272, 183, 19, 183, 18, 10)
                        .backButton(136, 183, 54, 183, 18, 11)
                        .refreshButton(26, 159, 38, 183, 15, 14)
                        .page(33, 16, 100, 150)
                        .page(157, 16, 100, 150)
                        .widget("separator", 0, 249, 100, 6)
                        .widget("small_frame", 54, 255, 20, 20)
                        .widget("medium_frame", 0, 255, 54, 54)
                        .widget("large_frame", 140, 182, 104, 104)
                        .widget("crafting_grid", 74, 255, 54, 54)
                        .widget("crafting_arrow", 73, 183, 16, 13)
                        .widget("crafting_flame", 89, 183, 14, 14)
                        .build(output);
            }
        }
    }

}
