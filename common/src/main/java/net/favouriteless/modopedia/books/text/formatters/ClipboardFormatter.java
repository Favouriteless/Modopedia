package net.favouriteless.modopedia.books.text.formatters;

import net.favouriteless.modopedia.api.text.StyleStack;
import net.favouriteless.modopedia.api.text.TextFormatter;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.ClickEvent.Action;

public class ClipboardFormatter implements TextFormatter {

    @Override
    public boolean matches(String tag) {
        return tag.startsWith("clip:");
    }

    @Override
    public void apply(StyleStack styleStack, String tag) {
        String text = tag.substring(2);
        styleStack.modify(style -> style.withClickEvent(new ClickEvent(Action.COPY_TO_CLIPBOARD, text)));
    }

}
