package net.favouriteless.modopedia.util.common;

import net.favouriteless.modopedia.common.init.MDataComponents;
import net.favouriteless.modopedia.common.init.MItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class InventoryUtils {

    public static boolean hasBook(Player player, ResourceLocation book) {
        for(ItemStack item : player.getInventory().items) {
            if(item.is(MItems.BOOK.get())) {
                if(book.equals(item.get(MDataComponents.BOOK.get())))
                    return true;
            }
        }
        return false;
    }

}
