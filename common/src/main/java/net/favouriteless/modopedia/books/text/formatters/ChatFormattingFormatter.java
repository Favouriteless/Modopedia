package net.favouriteless.modopedia.books.text.formatters;

import net.favouriteless.modopedia.api.text.StyleStack;
import net.favouriteless.modopedia.api.text.TextFormatter;
import net.minecraft.ChatFormatting;

public record ChatFormattingFormatter(String tag, ChatFormatting format) implements TextFormatter {

    @Override
    public boolean matches(String tag) {
        return tag.equals(this.tag);
    }

    @Override
    public void apply(StyleStack styleStack, String tag) {
        styleStack.modify(style -> style.applyFormat(format));
    }

}
