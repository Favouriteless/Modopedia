package net.favouriteless.modopedia.common.data_components;

import net.favouriteless.modopedia.platform.CommonServices;
import net.minecraft.core.component.DataComponentType;

import java.util.function.Supplier;

public class ModopediaDataComponents {

    public static Supplier<DataComponentType<BookDataComponent>> BOOK = register("book",() -> DataComponentType.<BookDataComponent>builder()
            .persistent(BookDataComponent.PERSISTENT_CODEC).networkSynchronized(BookDataComponent.STREAM_CODEC)
            .cacheEncoding().build()
    );

    public static <T extends DataComponentType<C>, C> Supplier<T> register(String name, Supplier<T> supplier) {
        return CommonServices.COMMON_REGISTRY.registerDataComponent(name, supplier);
    }

    public void load() {}

}
