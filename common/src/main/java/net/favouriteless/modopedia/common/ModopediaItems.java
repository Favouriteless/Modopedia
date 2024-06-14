package net.favouriteless.modopedia.common;

import net.favouriteless.modopedia.platform.CommonServices;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

import java.util.function.Supplier;

public class ModopediaItems {

    public static final Supplier<Item> BOOK = register("book", () -> new Item(new Properties()));

    private static <T extends Item> Supplier<T> register(String name, Supplier<T> itemSupplier) {
        return CommonServices.COMMON_REGISTRY.registerItem(name, itemSupplier);
    }

    public static void load() {} // Bootstrap method

}
