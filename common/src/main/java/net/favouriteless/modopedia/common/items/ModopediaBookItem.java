package net.favouriteless.modopedia.common.items;

import net.favouriteless.modopedia.api.BookRegistry;
import net.favouriteless.modopedia.client.ModopediaClient;
import net.favouriteless.modopedia.common.data_components.ModopediaDataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ModopediaBookItem extends Item {

    public ModopediaBookItem() {
        super(new Properties()
                .stacksTo(1)
                .component(ModopediaDataComponents.BOOK.get(), ResourceLocation.fromNamespaceAndPath("test_books", "testbook"))
        );
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(level.isClientSide()) {
            ItemStack stack = player.getItemInHand(hand);
            if(stack.has(ModopediaDataComponents.BOOK.get()))
                ModopediaClient.tryOpenBook(BookRegistry.get().getBook(stack.get(ModopediaDataComponents.BOOK.get())));
        }
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

}
