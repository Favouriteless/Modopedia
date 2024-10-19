package net.favouriteless.modopedia.book.text.formatters;

import net.favouriteless.modopedia.api.text.StyleStack;
import net.favouriteless.modopedia.api.text.TextFormatter;

import java.util.function.BiConsumer;

public class SimpleFormatter implements TextFormatter {

    private final String[] tagVariants;
    private final BiConsumer<StyleStack, String> applyConsumer;

    public SimpleFormatter(BiConsumer<StyleStack, String> applyConsumer, String... tagVariants) {
        this.tagVariants = tagVariants;
        this.applyConsumer = applyConsumer;
    }

    @Override
    public boolean matches(String tag) {
        for(String test : tagVariants) {
            if(tag.equals(test))
                return true;
        }
        return false;
    }

    @Override
    public void apply(StyleStack styleStack, String tag) {
        applyConsumer.accept(styleStack, tag);
    }

}
