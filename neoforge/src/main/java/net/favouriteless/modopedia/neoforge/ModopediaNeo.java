package net.favouriteless.modopedia.neoforge;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.common.network.packets.client.ClearBooksPayload;
import net.favouriteless.modopedia.common.network.packets.client.ReloadBookContentPayload;
import net.favouriteless.modopedia.common.network.packets.client.SyncBookPayload;
import net.favouriteless.modopedia.platform.services.NeoCommonRegistryHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(Modopedia.MOD_ID)
public class ModopediaNeo {

    public ModopediaNeo(IEventBus bus, ModContainer container) {
        NeoCommonRegistryHelper.ITEM_REGISTRY.register(bus);
        NeoCommonRegistryHelper.DATA_COMPONENT_REGISTRY.register(bus);
        Modopedia.init();


        bus.addListener(this::registerPayloads);
    }

    public void registerPayloads(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToClient(ClearBooksPayload.TYPE, ClearBooksPayload.STREAM_CODEC, (payload, context) -> payload.handle());
        registrar.playToClient(SyncBookPayload.TYPE, SyncBookPayload.STREAM_CODEC, (payload, context) -> payload.handle());
        registrar.playToClient(ReloadBookContentPayload.TYPE, ReloadBookContentPayload.STREAM_CODEC, (payload, context) -> payload.handle());
    }

}
