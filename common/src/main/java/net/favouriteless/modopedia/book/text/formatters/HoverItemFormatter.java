package net.favouriteless.modopedia.book.text.formatters;

import com.mojang.brigadier.StringReader;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.text.StyleStack;
import net.favouriteless.modopedia.api.text.TextFormatter;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.commands.arguments.item.ItemParser.ItemResult;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.HoverEvent.Action;
import net.minecraft.network.chat.HoverEvent.ItemStackInfo;
import net.minecraft.world.item.ItemStack;

public class HoverItemFormatter implements TextFormatter {

    @Override
    public boolean matches(String tag) {
        return tag.startsWith("hi:");
    }

    @Override
    public void apply(StyleStack stack, String tag) {
        ItemParser parser = new ItemParser(Minecraft.getInstance().level.registryAccess());
        String itemString = tag.substring(3);
        try {
            ItemResult result = parser.parse(new StringReader(itemString));
            ItemStack item = new ItemStack(result.item(), 1, result.components());
            stack.modify(style -> style.withHoverEvent(new HoverEvent(Action.SHOW_ITEM, new ItemStackInfo(item))));
        }
        catch(Exception e) {
            Modopedia.LOG.error("Invalid hover item: {}", itemString);
        }
    }

}
