package net.favouriteless.modopedia.books.text.formatters;

import net.favouriteless.modopedia.api.text.StyleStack;
import net.favouriteless.modopedia.api.text.TextFormatter;
import net.minecraft.network.chat.Style;

public class EndColorFormatter implements TextFormatter {

    @Override
    public boolean matches(String tag) {
        return tag.startsWith("/c");
    }

    @Override
    public void apply(StyleStack styleStack, String tag) {
        styleStack.push(Style.EMPTY);
        styleStack.modify(style -> style.withColor(styleStack.getBaseStyle().getColor()));
    }

}
