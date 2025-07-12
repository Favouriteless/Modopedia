package net.favouriteless.modopedia.fabric.common;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.registries.common.BookRegistry;
import net.favouriteless.modopedia.common.CommonEvents;
import net.favouriteless.modopedia.common.init.MDataComponents;
import net.favouriteless.modopedia.common.init.MItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;

public class FabricCommonEvents {

    public static void register() {
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register(FabricCommonEvents::onDatapackSync);

        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((tab, entries) -> CommonEvents.onTabContents(
                BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(tab).orElseThrow(), i -> entries.accept(i, TabVisibility.PARENT_TAB_ONLY))
        );

    }

    public static void onDatapackSync(ServerPlayer player, boolean joined) {
        CommonEvents.datapackSync(player);
    }

}
