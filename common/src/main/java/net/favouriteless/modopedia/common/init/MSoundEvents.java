package net.favouriteless.modopedia.common.init;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.platform.CommonServices;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class MSoundEvents {

    public static final Holder<SoundEvent> BOOK_OPEN = register("book_open", () -> SoundEvent.createVariableRangeEvent(Modopedia.id("book_open")));
    public static final Holder<SoundEvent> BOOK_FLIP = register("book_flip", () -> SoundEvent.createVariableRangeEvent(Modopedia.id("book_flip")));

    private static <T extends SoundEvent> Holder<T> register(String name, Supplier<T> soundSupplier) {
        return CommonServices.COMMON_REGISTRY.registerSound(name, soundSupplier);
    }

    public static void load() {} // Bootstrap method

}
