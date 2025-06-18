package net.favouriteless.modopedia.neoforge.datagen;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.datagen.example.MExampleBookProvider;
import net.favouriteless.modopedia.neoforge.datagen.providers.MBookTextureProvider;
import net.favouriteless.modopedia.neoforge.datagen.providers.MItemModelProvider;
import net.favouriteless.modopedia.neoforge.datagen.providers.MLanguageProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Modopedia.MOD_ID, bus = Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();
        CompletableFuture<Provider> provider = event.getLookupProvider();

        gen.addProvider(true, new MItemModelProvider(output, fileHelper));
        gen.addProvider(true, new MLanguageProvider(output));
        gen.addProvider(true, new MBookTextureProvider(provider, output));

        // Example Providers
        gen.addProvider(false, new MExampleBookProvider(provider, output));
    }

}
