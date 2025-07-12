package net.favouriteless.modopedia.common;

import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.registries.common.BookRegistry;
import net.favouriteless.modopedia.common.init.MDataComponents;
import net.favouriteless.modopedia.common.init.MItems;
import net.favouriteless.modopedia.common.network.packets.client.ClearBooksPayload;
import net.favouriteless.modopedia.common.network.packets.client.SyncBookPayload;
import net.favouriteless.modopedia.platform.CommonServices;
import net.favouriteless.modopedia.platform.services.INetworkHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class CommonEvents {

    public static void datapackSync(ServerPlayer player) {
        INetworkHelper networkHelper = CommonServices.NETWORK;

        networkHelper.sendToPlayer(new ClearBooksPayload(), player); // When reloading clear the client's book registry and sync new ones; contents will sync automatically
        BookRegistry.get().getBooks().forEach(book -> networkHelper.sendToPlayer(new SyncBookPayload(BookRegistry.get().getId(book), book), player));
    }

    public static void onTabContents(ResourceKey<CreativeModeTab> tab, Consumer<ItemStack> output) {
        for(Book book : BookRegistry.get().getBooks()) {
            if(!tab.equals(CreativeModeTabs.SEARCH) && !tab.equals(book.getCreativeTab()))
                continue;

            ItemStack item = new ItemStack(MItems.BOOK.get(), 1);
            item.set(MDataComponents.BOOK.get(), BookRegistry.get().getId(book));
            output.accept(item);
        }
    }

}
