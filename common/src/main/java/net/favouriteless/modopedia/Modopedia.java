package net.favouriteless.modopedia;

import net.favouriteless.modopedia.common.data_components.MDataComponents;
import net.favouriteless.modopedia.common.items.MItems;
import net.favouriteless.modopedia.common.reload_listeners.BookReloadListener;
import net.favouriteless.modopedia.platform.CommonServices;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Modopedia {

    public static final String MOD_ID = "modopedia";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        MDataComponents.load();
        MItems.load();

        CommonServices.COMMON_REGISTRY.registerReloadListener(Modopedia.id("books"), new BookReloadListener(MOD_ID + "/books"));
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

}
