package net.favouriteless.modopedia.common;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.registries.common.BookRegistry;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.client.BookOpenHandler;
import net.favouriteless.modopedia.common.init.MDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
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
        if(book != null && book.getSubtitle() != null)
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

    // Soft-implement NeoForge's IItemExtension#getCreatorModId method. This is to allow JEI, REI etc. to include modopedia books in searches for other mod IDs.
    // Potentially this will soft-implement it for Fabric API too but that may or may not be added by the time you read this comment.
    public String getCreatorModId(ItemStack item) {
        ResourceLocation book = item.get(MDataComponents.BOOK.get());
        return book != null ? book.getNamespace() : BuiltInRegistries.ITEM.getKey(item.getItem()).getNamespace();
    }

    public static ResourceLocation getBookId(ItemStack stack) {
        return stack.get(MDataComponents.BOOK.get());
    }

}
