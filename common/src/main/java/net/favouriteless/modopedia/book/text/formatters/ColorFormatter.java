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
    public void apply(StyleStack stack, String tag) {
        String colour = tag.substring(2);

        if(colour.matches("#[a-fA-F0-9]{6}")) { // Hex colour with #
            stack.modify(style -> style.withColor(Integer.decode(colour)));
            return;
        }
        else if(colour.matches("\\d{1,2}")) { // ChatFormatting ID (1 or 2 digit int)
            ChatFormatting formatting = ChatFormatting.getById(Integer.parseUnsignedInt(colour));
            if(formatting != null) {
                stack.modify(style -> style.withColor(formatting));
                return;
            }
        }
        else if(colour.matches("[a-zA-Z]+")){ // ChatFormatting name (word)
            ChatFormatting formatting = ChatFormatting.getByName(colour.toUpperCase());
            if(formatting != null) {
                stack.modify(style -> style.withColor(formatting));
                return;
            }
        }
        stack.pop();
        throw new IllegalArgumentException(colour + " is not a valid colour ID, name or hex value.");
    }

}
