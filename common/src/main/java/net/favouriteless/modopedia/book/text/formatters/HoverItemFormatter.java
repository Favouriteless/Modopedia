package net.favouriteless.modopedia.book.text.formatters;

import net.favouriteless.modopedia.api.text.StyleStack;
import net.favouriteless.modopedia.api.text.TextFormatter;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.commands.arguments.item.ItemParser.ItemResult;

public class HoverItemFormatter implements TextFormatter {

    @Override
    public boolean matches(String tag) {
        return tag.startsWith("hi:");
    }

    @Override
    public void apply(StyleStack styleStack, String tag) {
        String itemString = tag.substring(3);

//        ItemResult result = ItemParser.
//
//        throw new IllegalArgumentException(colour + " is not a valid colour ID, name or hex value.");
    }

}
