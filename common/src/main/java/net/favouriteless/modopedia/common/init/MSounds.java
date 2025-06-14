package net.favouriteless.modopedia.common.init;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.platform.CommonServices;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class MSounds {

    public static final Supplier<SoundEvent> BOOK_OPEN = register("book_open", () -> SoundEvent.createVariableRangeEvent(Modopedia.id("book_open")));
    public static final Supplier<SoundEvent> BOOK_FLIP = register("book_flip", () -> SoundEvent.createVariableRangeEvent(Modopedia.id("book_flip")));

    private static <T extends SoundEvent> Supplier<T> register(String name, Supplier<T> soundSupplier) {
        return CommonServices.COMMON_REGISTRY.registerSound(name, soundSupplier);
    }

    public static void load() {} // Bootstrap method

}
