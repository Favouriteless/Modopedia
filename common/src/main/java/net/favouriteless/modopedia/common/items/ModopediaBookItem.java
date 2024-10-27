package net.favouriteless.modopedia.common.items;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.BookRegistry;
import net.favouriteless.modopedia.client.ModopediaClient;
import net.favouriteless.modopedia.common.data_components.BookDataComponent;
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
                .component(ModopediaDataComponents.BOOK.get(), new BookDataComponent(Modopedia.id("testbook")))
        );
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(level.isClientSide()) {
            ItemStack stack = player.getItemInHand(hand);
            BookDataComponent data = stack.get(ModopediaDataComponents.BOOK.get());

            ResourceLocation id = data.id();
            if(!id.equals(Modopedia.id("none")))
                ModopediaClient.tryOpenBook(BookRegistry.get().getBook(data.id()));
        }
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

}
