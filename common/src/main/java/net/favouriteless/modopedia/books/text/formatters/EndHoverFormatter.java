package net.favouriteless.modopedia.books.text.formatters;

import net.favouriteless.modopedia.api.text.StyleStack;
import net.favouriteless.modopedia.api.text.TextFormatter;

public class EndHoverFormatter implements TextFormatter {

    @Override
    public boolean matches(String tag) {
        return tag.equals("/t");
    }

    @Override
    public void apply(StyleStack styleStack, String tag) {
        styleStack.modify(style -> style.withHoverEvent(null));
    }

}
