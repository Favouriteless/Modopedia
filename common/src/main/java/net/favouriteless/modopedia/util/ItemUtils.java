package net.favouriteless.modopedia.util;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.world.item.ItemStack;

public class ItemUtils {

    public static ItemStack itemFromJson(JsonElement jsonElement) {
        return ItemStack.CODEC.decode(JsonOps.INSTANCE, jsonElement).getOrThrow().getFirst();
    }

}
