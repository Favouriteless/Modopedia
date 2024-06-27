package net.favouriteless.modopedia.books.text.formatters;

import net.favouriteless.modopedia.api.text.StyleStack;
import net.favouriteless.modopedia.api.text.TextFormatter;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.ClickEvent.Action;

public class CommandFormatter implements TextFormatter {

    @Override
    public boolean matches(String tag) {
        return tag.startsWith("cmd:");
    }

    @Override
    public void apply(StyleStack styleStack, String tag) {
        String command = tag.substring(4);

        if(command.length() < 2 || !command.startsWith("/")) {
            styleStack.pop();
            throw new IllegalArgumentException(command + " is not a valid command.");
        }

        styleStack.modify(style -> style.withClickEvent(new ClickEvent(Action.RUN_COMMAND, command)));
    }

}
