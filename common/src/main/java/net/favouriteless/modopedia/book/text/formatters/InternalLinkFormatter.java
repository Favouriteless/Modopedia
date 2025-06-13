package net.favouriteless.modopedia.book.text.formatters;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.text.StyleStack;
import net.favouriteless.modopedia.api.text.TextFormatter;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;

public class InternalLinkFormatter implements TextFormatter {

    private final String prefix;
    private final String type;

    public InternalLinkFormatter(String prefix, String type) {
        this.prefix = prefix;
        this.type = type;
    }

    @Override
    public boolean matches(String tag) {
        return tag.startsWith(prefix);
    }

    @Override
    public void apply(StyleStack stack, String tag) {
        String id = tag.substring(prefix.length());
        String command = String.format("/modopedia open %s \"%s\"", type, id);

        stack.modify(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command)));
        stack.modify(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable(Modopedia.translation("tooltip", type + "_link")))));
    }

}
