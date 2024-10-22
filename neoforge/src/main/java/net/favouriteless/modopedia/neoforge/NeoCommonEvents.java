package net.favouriteless.modopedia.neoforge;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.common.CommonEvents;
import net.favouriteless.modopedia.platform.services.NeoCommonRegistryHelper;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

@EventBusSubscriber(modid = Modopedia.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class NeoCommonEvents {

    @SubscribeEvent
    public static void onDatapackSync(final OnDatapackSyncEvent event) {
        event.getRelevantPlayers().forEach(CommonEvents::datapackSync);
    }

    @SubscribeEvent
    public static void onRegisterReloadListeners(AddReloadListenerEvent event) {
        for(SimpleJsonResourceReloadListener listener : NeoCommonRegistryHelper.dataLoaders) {
            event.addListener(listener);
        }
    }

}
