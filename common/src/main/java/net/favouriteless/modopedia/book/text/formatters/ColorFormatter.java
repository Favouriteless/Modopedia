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
        String colour = tag.substring(2);

        if(colour.matches("#[a-fA-F0-9]{6}")) { // Hex colour with #
            styleStack.modify(style -> style.withColor(Integer.decode(colour)));
            return;
        }
        else if(colour.matches("\\d{1,2}")) { // ChatFormatting ID (1 or 2 digit int)
            ChatFormatting formatting = ChatFormatting.getById(Integer.parseUnsignedInt(colour));
            if(formatting != null) {
                styleStack.modify(style -> style.withColor(formatting));
                return;
            }
        }
        else if(colour.matches("[a-zA-Z]+")){ // ChatFormatting name (word)
            ChatFormatting formatting = ChatFormatting.getByName(colour.toUpperCase());
            if(formatting != null) {
                styleStack.modify(style -> style.withColor(formatting));
                return;
            }
        }
        styleStack.pop();
        throw new IllegalArgumentException(colour + " is not a valid colour ID, name or hex value.");
    }

}
