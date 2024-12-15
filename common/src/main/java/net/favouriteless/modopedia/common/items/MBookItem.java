package net.favouriteless.modopedia.common.items;

import net.favouriteless.modopedia.api.BookRegistry;
import net.favouriteless.modopedia.client.ModopediaClient;
import net.favouriteless.modopedia.common.data_components.MDataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MBookItem extends Item {

    public MBookItem() {
        super(new Properties()
                .stacksTo(1)
                .component(MDataComponents.BOOK.get(), ResourceLocation.fromNamespaceAndPath("test_books", "testbook"))
        );
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(level.isClientSide()) {
            ItemStack stack = player.getItemInHand(hand);
            if(stack.has(MDataComponents.BOOK.get()))
                ModopediaClient.tryOpenBook(stack.get(MDataComponents.BOOK.get()));
        }
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

}
