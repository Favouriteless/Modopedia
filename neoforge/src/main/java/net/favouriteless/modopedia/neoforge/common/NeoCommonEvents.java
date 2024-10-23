package net.favouriteless.modopedia.neoforge.common;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.common.CommonEvents;
import net.favouriteless.modopedia.platform.services.NeoCommonRegistryHelper;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

@EventBusSubscriber(modid = Modopedia.MOD_ID, bus = Bus.GAME)
public class NeoCommonEvents {

    @SubscribeEvent
    public static void onDatapackSync(final OnDatapackSyncEvent event) {
        event.getRelevantPlayers().forEach(CommonEvents::datapackSync);
    }

    @SubscribeEvent
    public static void onRegisterReloadListeners(final AddReloadListenerEvent event) {
        for(PreparableReloadListener listener : NeoCommonRegistryHelper.RELOAD_LISTENERS) {
            event.addListener(listener);
        }
    }

}
