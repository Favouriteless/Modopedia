package net.favouriteless.modopedia.book.text.formatters;

import net.favouriteless.modopedia.api.text.StyleStack;
import net.favouriteless.modopedia.api.text.TextFormatter;
import net.minecraft.ChatFormatting;

public class ColorFormatter implements TextFormatter {

    @Override
    public boolean matches(String tag) {
        return tag.startsWith("c:");
    }

    @Override
    public void apply(StyleStack styleStack, String tag) {
        String colorString = tag.substring(2);

        if(colorString.matches("\\d+")) {
            ChatFormatting formatting = ChatFormatting.getById(Integer.parseUnsignedInt(colorString));
            if(formatting == null) {
                styleStack.pop();
                throw new IllegalArgumentException(colorString + " is not a valid colour ID, name or hex value.");
            }

            styleStack.modify(style -> style.withColor(formatting));
        }
        else if(colorString.matches("#[a-fA-F0-9]{6}")) {
            styleStack.modify(style -> style.withColor(Integer.decode(colorString)));
        }
        else {
            ChatFormatting formatting = ChatFormatting.getByName(colorString.toUpperCase());
            if(formatting == null) {
                styleStack.pop();
                throw new IllegalArgumentException(colorString + " is not a valid colour ID, name or hex value.");
            }
            styleStack.modify(style -> style.withColor(formatting));
        }
    }

}
