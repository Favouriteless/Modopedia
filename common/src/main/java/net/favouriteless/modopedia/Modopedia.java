package net.favouriteless.modopedia;

import net.favouriteless.modopedia.common.DefaultPageComponents;
import net.favouriteless.modopedia.common.data_components.ModopediaDataComponents;
import net.favouriteless.modopedia.common.items.ModopediaItems;
import net.favouriteless.modopedia.platform.CommonServices;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Modopedia {

    public static final String MOD_ID = "modopedia";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        ModopediaDataComponents.load();
        ModopediaItems.load();
        DefaultPageComponents.init();
        CommonServices.COMMON_REGISTRY.registerReloadListener(Modopedia.id("book"), new BookReloadListener());
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

}
