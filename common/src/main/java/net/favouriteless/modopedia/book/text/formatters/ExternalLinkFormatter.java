package net.favouriteless.modopedia.book.text.formatters;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.text.StyleStack;
import net.favouriteless.modopedia.api.text.TextFormatter;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;

public class ExternalLinkFormatter implements TextFormatter {

    @Override
    public boolean matches(String tag) {
        return tag.matches("l:https?://.*");
    }

    @Override
    public void apply(StyleStack stack, String tag) {
        String url = tag.substring(2);
        stack.modify(style -> style.withClickEvent(new ClickEvent(Action.OPEN_URL, url)));
        stack.modify(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable(Modopedia.translation("tooltip", "url"), url))));
    }

}
