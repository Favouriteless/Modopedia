package net.favouriteless.modopedia.neoforge;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.registries.common.BookRegistry;
import net.favouriteless.modopedia.common.CommonConfig;
import net.favouriteless.modopedia.common.ServerConfig;
import net.favouriteless.modopedia.common.init.MDataComponents;
import net.favouriteless.modopedia.common.init.MItems;
import net.favouriteless.modopedia.common.network.packets.client.*;
import net.favouriteless.modopedia.platform.services.NeoCommonRegistryHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(Modopedia.MOD_ID)
@EventBusSubscriber(modid = Modopedia.MOD_ID, bus = Bus.MOD)
public class ModopediaNeo {

    public ModopediaNeo(IEventBus bus, ModContainer container) {
        NeoCommonRegistryHelper.ITEM_REGISTRY.register(bus);
        NeoCommonRegistryHelper.DATA_COMPONENT_REGISTRY.register(bus);
        NeoCommonRegistryHelper.SOUND_EVENT_REGISTRY.register(bus);
        Modopedia.init();

        container.registerConfig(Type.COMMON, CommonConfig.SPEC, "modopedia-common.toml");
        container.registerConfig(Type.SERVER, ServerConfig.SPEC, "modopedia-server.toml");
    }

    @SubscribeEvent
    public static void registerPayloads(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToClient(ClearBooksPayload.TYPE, ClearBooksPayload.STREAM_CODEC, (payload, context) -> payload.handle());
        registrar.playToClient(SyncBookPayload.TYPE, SyncBookPayload.STREAM_CODEC, (payload, context) -> payload.handle());
        registrar.playToClient(ReloadBookContentPayload.TYPE, ReloadBookContentPayload.STREAM_CODEC, (payload, context) -> payload.handle());
        registrar.playToClient(OpenBookPayload.TYPE, OpenBookPayload.STREAM_CODEC, (payload, context) -> payload.handle());
        registrar.playToClient(OpenCategoryPayload.TYPE, OpenCategoryPayload.STREAM_CODEC, (payload, context) -> payload.handle());
        registrar.playToClient(OpenEntryPayload.TYPE, OpenEntryPayload.STREAM_CODEC, (payload, context) -> payload.handle());
    }

    @SubscribeEvent
    public static void onTabContents(final BuildCreativeModeTabContentsEvent event) {
        for(Book book : BookRegistry.get().getBooks()) {
            ResourceKey<CreativeModeTab> key = event.getTabKey();
            if(!key.equals(book.getCreativeTab()))
                continue;

            ItemStack item = new ItemStack(MItems.BOOK.get(), 1);
            item.set(MDataComponents.BOOK, BookRegistry.get().getId(book));
            event.accept(item);
        }
    }

}
