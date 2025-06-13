package net.favouriteless.modopedia.book.registries;

import net.favouriteless.modopedia.api.text.StyleStack;
import net.favouriteless.modopedia.api.text.TextFormatter;
import net.favouriteless.modopedia.book.text.formatters.ColorFormatter;
import net.favouriteless.modopedia.book.text.formatters.HoverItemFormatter;
import net.favouriteless.modopedia.book.text.formatters.SimpleFormatter;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Set;

public class TextFormatterRegistry {

    private static final Set<TextFormatter> formatters = new HashSet<>();

    static {
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
        register(new SimpleFormatter((stack, tag) -> stack.modify(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, tag.substring(2)))), "l:https?://.*"));
        register(new SimpleFormatter((stack, tag) -> stack.modify(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, tag.substring(4)))), "cmd:/.+"));
        register(new SimpleFormatter((stack, tag) -> stack.modify(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, tag.substring(5)))), "clip:.+"));
        register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withClickEvent(null)), "/l", "/cmd", "/clip"));
        register(new SimpleFormatter((stack, tag) -> stack.modify(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal(tag.substring(2))))), "t:.+"));
        register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withHoverEvent(null)), "/t"));
        register(new SimpleFormatter((stack, tag) -> stack.modify(style -> style.withColor(stack.getBaseStyle().getColor())), "/c"));
        register(new SimpleFormatter((stack, tag) -> stack.modify(style -> stack.getBaseStyle()), "^$")); // this matches an empty string
        register(new SimpleFormatter((stack, tag) -> stack.modify(style -> style.withFont(ResourceLocation.parse(tag.substring(2)))), "f:.+"));
        register(new SimpleFormatter((stack, tag) -> stack.modify(style -> style.withFont(stack.getBaseStyle().getFont())), "/f"));
        register(new SimpleFormatter((stack, tag) -> stack.modify(style -> style.withHoverEvent(null)), "/hi"));

//        register(new SimpleFormatter((stack, tag) -> stack.modify(style -> style.withClickEvent(ClickEvent.Action.))));

        // These formatters needed special handling.
        register(new ColorFormatter());
        register(new HoverItemFormatter());
    }

    public static void register(TextFormatter formatter) {
        formatters.add(formatter);
    }

    public static void tryApply(StyleStack styleStack, String tag) {
        for(TextFormatter formatter : formatters) {
            if(formatter.matches(tag)) {
                formatter.apply(styleStack, tag);
                return;
            }
        }
    }

}
