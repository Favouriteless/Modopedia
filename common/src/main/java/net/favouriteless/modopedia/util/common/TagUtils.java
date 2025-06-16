package net.favouriteless.modopedia.util.common;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Iterator;
import java.util.List;

public class TagUtils {

    public static TagKey<Item> findItemTag(List<ItemStack> items) {
        Iterator<Pair<TagKey<Item>, Named<Item>>> iterator = BuiltInRegistries.ITEM.getTags().iterator();

        outerLoop: while(iterator.hasNext()) {
            Pair<TagKey<Item>, Named<Item>> tag = iterator.next();

            int count = 0;
            for(ItemStack stack : items) {
                if(!tag.getSecond().contains(stack.getItemHolder()))
                    continue outerLoop; // I never get to use labels this is cool
                count++;
            }

            if(tag.getSecond().size() == count)
                return tag.getFirst();
        }
        return null;
    }

}
