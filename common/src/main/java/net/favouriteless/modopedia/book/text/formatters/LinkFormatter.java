package net.favouriteless.modopedia.book.text.formatters;

import net.favouriteless.modopedia.api.text.StyleStack;
import net.favouriteless.modopedia.api.text.TextFormatter;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.ClickEvent.Action;

public class LinkFormatter implements TextFormatter {

    @Override
    public boolean matches(String tag) {
        return tag.startsWith("l:");
    }

    @Override
    public void apply(StyleStack styleStack, String tag) {
        String link = tag.substring(2);
        styleStack.modify(style -> style.withClickEvent(new ClickEvent(Action.OPEN_URL, link)));
    }

}
