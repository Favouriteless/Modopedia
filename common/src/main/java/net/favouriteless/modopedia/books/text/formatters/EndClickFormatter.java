package net.favouriteless.modopedia.books.text.formatters;

import net.favouriteless.modopedia.api.text.StyleStack;
import net.favouriteless.modopedia.api.text.TextFormatter;

public class EndClickFormatter implements TextFormatter {

    @Override
    public boolean matches(String tag) {
        return tag.equals("/l") || tag.equals("/cmd") || tag.equals("/clip");
    }

    @Override
    public void apply(StyleStack styleStack, String tag) {
        styleStack.modify(style -> style.withClickEvent(null));
    }

}
