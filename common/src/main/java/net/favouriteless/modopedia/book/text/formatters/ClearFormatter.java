package net.favouriteless.modopedia.book.text.formatters;

import net.favouriteless.modopedia.api.text.StyleStack;
import net.favouriteless.modopedia.api.text.TextFormatter;

public class ClearFormatter implements TextFormatter {

    @Override
    public boolean matches(String tag) {
        return tag.isEmpty();
    }

    @Override
    public void apply(StyleStack styleStack, String tag) {
        styleStack.modify(style -> styleStack.getBaseStyle());
    }

}
