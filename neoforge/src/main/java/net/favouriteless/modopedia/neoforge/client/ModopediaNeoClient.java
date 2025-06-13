package net.favouriteless.modopedia.neoforge.client;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.client.MBookModel;
import net.favouriteless.modopedia.client.ModopediaClient;
import net.favouriteless.modopedia.platform.services.NeoClientRegistryHelper;
import net.favouriteless.modopedia.util.ResourceUtils;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@Mod(value = Modopedia.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Modopedia.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ModopediaNeoClient {

    public ModopediaNeoClient(IEventBus bus, ModContainer container) {
        ModopediaClient.init();
    }

    @SubscribeEvent
    public static void registerClientReloadListeners(final RegisterClientReloadListenersEvent event) {
        for(PreparableReloadListener listener : NeoClientRegistryHelper.RELOAD_LISTENERS) {
            event.registerReloadListener(listener);
        }
    }

    @SubscribeEvent
    public static void registerModels(ModelEvent.RegisterAdditional event) {
        ResourceUtils.getBookModels()
                .map(ModelResourceLocation::standalone)
                .forEach(event::register);
    }

    @SubscribeEvent
    public static void modifyModels(ModelEvent.ModifyBakingResult event) {
        ModelResourceLocation key = ModelResourceLocation.inventory(Modopedia.id("book"));
        event.getModels().computeIfPresent(key, (k, original) ->
                new MBookModel(original, m -> Minecraft.getInstance().getModelManager().getModel(ModelResourceLocation.standalone(m)))
        );
    }


    @SubscribeEvent
    public static void registerKeybinds(final RegisterKeyMappingsEvent event) {
        for(KeyMapping mapping : NeoClientRegistryHelper.KEY_MAPPINGS)
            event.register(mapping);
        NeoClientRegistryHelper.KEY_MAPPINGS.clear();
    }

}
