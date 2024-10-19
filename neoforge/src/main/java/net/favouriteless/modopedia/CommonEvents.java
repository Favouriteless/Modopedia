package net.favouriteless.modopedia;

import net.favouriteless.modopedia.platform.services.NeoForgeCommonRegistryHelper;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

@EventBusSubscriber(modid = Modopedia.MOD_ID, bus = Bus.GAME)
public class CommonEvents {

    @SubscribeEvent
    public static void onRegisterReloadListeners(AddReloadListenerEvent event) {
        for( SimpleJsonResourceReloadListener listener : NeoForgeCommonRegistryHelper.dataLoaders) {
            event.addListener(listener);
        }
    }
}
