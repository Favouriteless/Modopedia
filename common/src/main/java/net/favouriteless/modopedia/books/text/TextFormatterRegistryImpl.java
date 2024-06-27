package net.favouriteless.modopedia.books.text;

import net.favouriteless.modopedia.api.text.StyleStack;
import net.favouriteless.modopedia.api.text.TextFormatter;
import net.favouriteless.modopedia.api.text.TextFormatterRegistry;
import net.favouriteless.modopedia.books.text.formatters.*;
import net.minecraft.ChatFormatting;

import java.util.HashSet;
import java.util.Set;

public class TextFormatterRegistryImpl implements TextFormatterRegistry {

    public static TextFormatterRegistryImpl INSTANCE = new TextFormatterRegistryImpl();

    private final Set<TextFormatter> formatters = new HashSet<>();

    private TextFormatterRegistryImpl() {
        register(new ChatFormattingFormatter("b", ChatFormatting.BOLD));
        register(new EndBoldFormatter());
        register(new ChatFormattingFormatter("i", ChatFormatting.ITALIC));
        register(new EndItalicFormatter());
        register(new ChatFormattingFormatter("u", ChatFormatting.UNDERLINE));
        register(new EndUnderlineFormatter());
        register(new ChatFormattingFormatter("s", ChatFormatting.STRIKETHROUGH));
        register(new EndStrikethroughFormatter());
        register(new ChatFormattingFormatter("o", ChatFormatting.OBFUSCATED));
        register(new EndObfuscatedFormatter());
        register(new ColorFormatter());
        register(new ClearFormatter());
        register(new LinkFormatter());
        register(new ClipboardFormatter());
        register(new TooltipFormatter());
        register(new EndClickFormatter());
        register(new EndHoverFormatter());
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
