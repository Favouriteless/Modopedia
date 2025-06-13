package net.favouriteless.modopedia.book.registries.client;

import net.favouriteless.modopedia.api.registries.client.TextFormatterRegistry;
import net.favouriteless.modopedia.api.text.StyleStack;
import net.favouriteless.modopedia.api.text.TextFormatter;

import java.util.HashSet;
import java.util.Set;

public class TextFormatterRegistryImpl implements TextFormatterRegistry {

    public static final TextFormatterRegistryImpl INSTANCE = new TextFormatterRegistryImpl();

    private final Set<TextFormatter> formatters = new HashSet<>();

    private TextFormatterRegistryImpl() {}

    @Override
    public void register(TextFormatter formatter) {
        formatters.add(formatter);
    }

    public void tryApply(StyleStack styleStack, String tag) {
        for(TextFormatter formatter : formatters) {
            if(formatter.matches(tag)) {
                formatter.apply(styleStack, tag);
                return;
            }
        }
    }

}
