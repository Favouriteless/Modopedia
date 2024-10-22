package net.favouriteless.modopedia.book.text;

import net.favouriteless.modopedia.api.text.StyleStack;
import net.favouriteless.modopedia.api.text.TextFormatter;
import net.favouriteless.modopedia.api.TextFormatterRegistry;
import net.favouriteless.modopedia.book.text.formatters.*;

import java.util.HashSet;
import java.util.Set;

public class TextFormatterRegistryImpl implements TextFormatterRegistry {

    public static TextFormatterRegistryImpl INSTANCE = new TextFormatterRegistryImpl();

    private final Set<TextFormatter> formatters = new HashSet<>();

    private TextFormatterRegistryImpl() {
        register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withBold(true)), "b"));
        register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withItalic(true)), "i"));
        register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withUnderlined(true)), "u"));
        register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withStrikethrough(true)), "s"));
        register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withObfuscated(true)), "o"));
        register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withBold(false)), "/b"));
        register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withItalic(false)), "/i"));
        register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withUnderlined(false)), "/u"));
        register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withStrikethrough(false)), "/s"));
        register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withObfuscated(false)), "/o"));
        register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withClickEvent(null)), "/l", "/cmd", "/clip"));
        register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withHoverEvent(null)), "/t"));

        // These formatters needed special handling.
        register(new ColorFormatter());
        register(new ClearFormatter());
        register(new LinkFormatter());
        register(new ClipboardFormatter());
        register(new TooltipFormatter());
    }

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
