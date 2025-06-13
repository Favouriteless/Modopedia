package net.favouriteless.modopedia.common.init;

import net.favouriteless.modopedia.platform.CommonServices;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class MDataComponents {

    public static Supplier<DataComponentType<ResourceLocation>> BOOK = register("book",() -> DataComponentType.<ResourceLocation>builder()
            .persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC)
            .cacheEncoding().build()
    );

    public static <T extends DataComponentType<C>, C> Supplier<T> register(String name, Supplier<T> supplier) {
        return CommonServices.COMMON_REGISTRY.registerDataComponent(name, supplier);
    }

    public static void load() {}

}
