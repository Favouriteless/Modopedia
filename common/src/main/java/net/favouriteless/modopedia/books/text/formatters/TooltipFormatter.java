package net.favouriteless.modopedia.books.text.formatters;

import net.favouriteless.modopedia.api.text.StyleStack;
import net.favouriteless.modopedia.api.text.TextFormatter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.HoverEvent.Action;

public class TooltipFormatter implements TextFormatter {

    @Override
    public boolean matches(String tag) {
        return tag.startsWith("t:");
    }

    @Override
    public void apply(StyleStack styleStack, String tag) {
        String tooltip = tag.substring(2);
        styleStack.modify(style -> style.withHoverEvent(new HoverEvent(Action.SHOW_TEXT, Component.literal(tooltip))));
    }

}
