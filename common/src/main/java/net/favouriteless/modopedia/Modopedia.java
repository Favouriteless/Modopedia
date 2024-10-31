package net.favouriteless.modopedia;

import net.favouriteless.modopedia.book.components.DefaultPageComponents;
import net.favouriteless.modopedia.common.data_components.ModopediaDataComponents;
import net.favouriteless.modopedia.common.items.ModopediaItems;
import net.favouriteless.modopedia.common.reload_listeners.BookReloadListener;
import net.favouriteless.modopedia.platform.CommonServices;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Modopedia {

    public static final String MOD_ID = "modopedia";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);

    public static final String BOOK_DIRECTORY = Modopedia.MOD_ID + "_books";

    public static void init() {
        ModopediaDataComponents.load();
        ModopediaItems.load();
        DefaultPageComponents.load();

        CommonServices.COMMON_REGISTRY.registerReloadListener(Modopedia.id("books"), new BookReloadListener(BOOK_DIRECTORY));
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

}
