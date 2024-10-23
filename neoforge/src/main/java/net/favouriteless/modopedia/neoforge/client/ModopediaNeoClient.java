package net.favouriteless.modopedia.neoforge.client;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.client.ModopediaClient;
import net.favouriteless.modopedia.platform.services.NeoClientRegistryHelper;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

@Mod(value = Modopedia.MOD_ID, dist = Dist.CLIENT)
public class ModopediaNeoClient {

    public ModopediaNeoClient(IEventBus bus, ModContainer container) {
        ModopediaClient.init();

        bus.addListener(this::registerClientReloadListeners);
    }

    public void registerClientReloadListeners(final RegisterClientReloadListenersEvent event) {
        for(PreparableReloadListener listener : NeoClientRegistryHelper.RELOAD_LISTENERS) {
            event.registerReloadListener(listener);
        }
    }


}
