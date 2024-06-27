package net.favouriteless.modopedia.books.text.formatters;

import net.favouriteless.modopedia.api.text.StyleStack;
import net.favouriteless.modopedia.api.text.TextFormatter;

public class EndObfuscatedFormatter implements TextFormatter {

    @Override
    public boolean matches(String tag) {
        return tag.equals("/o");
    }

    @Override
    public void apply(StyleStack styleStack, String tag) {
        styleStack.modify(style -> style.withObfuscated(false));
    }

}
