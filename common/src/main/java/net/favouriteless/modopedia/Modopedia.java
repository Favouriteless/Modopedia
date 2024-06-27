package net.favouriteless.modopedia;

import net.favouriteless.modopedia.common.DefaultPageComponents;
import net.favouriteless.modopedia.common.ModopediaItems;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Modopedia {

    public static final String MOD_ID = "modopedia";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        ModopediaItems.load();
        DefaultPageComponents.init();
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

}
