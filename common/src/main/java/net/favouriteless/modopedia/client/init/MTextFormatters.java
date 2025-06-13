package net.favouriteless.modopedia.client.init;

import net.favouriteless.modopedia.api.registries.client.TextFormatterRegistry;
import net.favouriteless.modopedia.book.text.formatters.ColorFormatter;
import net.favouriteless.modopedia.book.text.formatters.HoverItemFormatter;
import net.favouriteless.modopedia.book.text.formatters.InternalLinkFormatter;
import net.favouriteless.modopedia.book.text.formatters.SimpleFormatter;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.resources.ResourceLocation;

public class MTextFormatters {
    
    public static void load() {
        TextFormatterRegistry registry = TextFormatterRegistry.get();

        registry.register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withBold(true)), "b"));
        registry.register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withItalic(true)), "i"));
        registry.register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withUnderlined(true)), "u"));
        registry.register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withStrikethrough(true)), "s"));
        registry.register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withObfuscated(true)), "o"));
        registry.register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withBold(false)), "/b"));
        registry.register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withItalic(false)), "/i"));
        registry.register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withUnderlined(false)), "/u"));
        registry.register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withStrikethrough(false)), "/s"));
        registry.register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withObfuscated(false)), "/o"));
        registry.register(new SimpleFormatter((stack, tag) -> stack.modify(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, tag.substring(2)))), "l:https?://.*"));
        registry.register(new SimpleFormatter((stack, tag) -> stack.modify(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, tag.substring(4)))), "cmd:/.+"));
        registry.register(new SimpleFormatter((stack, tag) -> stack.modify(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, tag.substring(5)))), "clip:.+"));
        registry.register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withClickEvent(null)), "/l", "/cmd", "/clip"));
        registry.register(new SimpleFormatter((stack, tag) -> stack.modify(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal(tag.substring(2))))), "t:.+"));
        registry.register(new SimpleFormatter((stack, tag) -> stack.modify(s -> s.withHoverEvent(null)), "/t", "/hi"));
        registry.register(new SimpleFormatter((stack, tag) -> stack.modify(style -> style.withColor(stack.getBaseStyle().getColor())), "/c"));
        registry.register(new SimpleFormatter((stack, tag) -> stack.modify(style -> stack.getBaseStyle()), "^$")); // this matches an empty string
        registry.register(new SimpleFormatter((stack, tag) -> stack.modify(style -> style.withFont(ResourceLocation.parse(tag.substring(2)))), "f:.+"));
        registry.register(new SimpleFormatter((stack, tag) -> stack.modify(style -> style.withFont(stack.getBaseStyle().getFont())), "/f"));
        registry.register(new SimpleFormatter((stack, tag) -> stack.modify(style -> style.withClickEvent(null).withHoverEvent(null)), "/cl", "/el"));

        // These formatters needed special handling.
        registry.register(new InternalLinkFormatter("cl:", "category"));
        registry.register(new InternalLinkFormatter("el:", "entry"));
        registry.register(new ColorFormatter());
        registry.register(new HoverItemFormatter());
    }

}
