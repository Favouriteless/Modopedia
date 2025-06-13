package net.favouriteless.modopedia.book.text.formatters;

import net.favouriteless.modopedia.api.text.StyleStack;
import net.favouriteless.modopedia.api.text.TextFormatter;

import java.util.function.BiConsumer;

public class SimpleFormatter implements TextFormatter {

    private final String[] tagRegexes;
    private final BiConsumer<StyleStack, String> applyConsumer;

    public SimpleFormatter(BiConsumer<StyleStack, String> applyConsumer, String... tagRegexes) {
        this.tagRegexes = tagRegexes;
        this.applyConsumer = applyConsumer;
    }

    @Override
    public boolean matches(String tag) {
        for(String test : tagRegexes) {
            if(tag.matches(test))
                return true;
        }
        return false;
    }

    @Override
    public void apply(StyleStack stack, String tag) {
        applyConsumer.accept(stack, tag);
    }

}
