package net.favouriteless.modopedia.fabric;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.registries.common.BookRegistry;
import net.favouriteless.modopedia.client.init.MKeyMappings;
import net.favouriteless.modopedia.common.CommonConfig;
import net.favouriteless.modopedia.common.ServerConfig;
import net.favouriteless.modopedia.common.init.MCommands;
import net.favouriteless.modopedia.common.init.MDataComponents;
import net.favouriteless.modopedia.common.init.MItems;
import net.favouriteless.modopedia.common.network.packets.client.*;
import net.favouriteless.modopedia.fabric.common.FabricCommonEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.config.ModConfig.Type;

public class ModopediaFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Modopedia.init();
        registerPackets();
        registerKeyMappings();
        FabricCommonEvents.register();

        CommandRegistrationCallback.EVENT.register((dispatcher, context, environment) -> MCommands.load(dispatcher, context));

        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((tab, entries) -> {
            for(Book book : BookRegistry.get().getBooks()) {
                ResourceKey<CreativeModeTab> key = BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(tab).orElseThrow();
                if(!key.equals(book.getCreativeTab()))
                    continue;

                ItemStack item = new ItemStack(MItems.BOOK.get(), 1);
                item.set(MDataComponents.BOOK.get(), BookRegistry.get().getId(book));
                entries.accept(item);
            }
        });
        NeoForgeConfigRegistry.INSTANCE.register(Modopedia.MOD_ID, Type.COMMON, CommonConfig.SPEC, "modopedia-common.toml");
        NeoForgeConfigRegistry.INSTANCE.register(Modopedia.MOD_ID, Type.SERVER, ServerConfig.SPEC, "modopedia-server.toml");
    }

    public void registerKeyMappings() {
        KeyBindingHelper.registerKeyBinding(MKeyMappings.KEY_STUDY);
    }

    public void registerPackets() {
        PayloadTypeRegistry.playS2C().register(ClearBooksPayload.TYPE, ClearBooksPayload.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(SyncBookPayload.TYPE, SyncBookPayload.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(ReloadBookContentPayload.TYPE, ReloadBookContentPayload.STREAM_CODEC);

        PayloadTypeRegistry.playS2C().register(OpenBookPayload.TYPE, OpenBookPayload.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(OpenCategoryPayload.TYPE, OpenCategoryPayload.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(OpenEntryPayload.TYPE, OpenEntryPayload.STREAM_CODEC);
    }

}
