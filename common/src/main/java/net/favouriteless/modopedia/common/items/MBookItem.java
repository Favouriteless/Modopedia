package net.favouriteless.modopedia.common.items;

import net.favouriteless.modopedia.api.registries.BookRegistry;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.client.BookOpenHandler;
import net.favouriteless.modopedia.client.ModopediaClient;
import net.favouriteless.modopedia.common.data_components.MDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class MBookItem extends Item {

    public MBookItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(level.isClientSide()) {
            ItemStack stack = player.getItemInHand(hand);
            if(stack.has(MDataComponents.BOOK.get()))
                BookOpenHandler.tryOpenBook(getBookId(stack));
        }
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        ResourceLocation bookId = getBookId(stack);
        if(bookId == null)
            return;

        Book book = BookRegistry.get().getBook(bookId);
        if(book != null)
            tooltip.add(Component.translatable(book.getSubtitle()).withStyle(ChatFormatting.GRAY));

        if(flag.isAdvanced())
            tooltip.add(Component.translatable("tooltip.modopedia.book_id", bookId.toString()).withStyle(ChatFormatting.DARK_GRAY));
    }

    @Override
    public Component getName(ItemStack stack) {
        Book book = BookRegistry.get().getBook(getBookId(stack));
        if(book != null) {
            return Component.translatable(book.getTitle());
        }

        return super.getName(stack);
    }

    public static ResourceLocation getBookId(ItemStack stack) {
        return stack.get(MDataComponents.BOOK.get());
    }

}
