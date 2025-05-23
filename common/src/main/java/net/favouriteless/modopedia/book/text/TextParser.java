package net.favouriteless.modopedia.book.text;

import com.mojang.datafixers.util.Pair;
import net.favouriteless.modopedia.book.registries.TextFormatterRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.ArrayList;
import java.util.List;

public class TextParser {

    public static final String FORMATTER_REGEX = "\\$\\([^$()]*\\)";

    public static List<TextChunk> parse(String rawText, int lineWidth, int lineHeight, Style baseStyle) {
        if(rawText == null || rawText.isBlank())
            return List.of();

        String[] split = rawText.splitWithDelimiters(FORMATTER_REGEX, 0); // Separate actual text and formatters

        TextState styleStack = new TextState(baseStyle);
        List<Component> paragraph = new ArrayList<>();

        boolean lastFormat = false;
        for(String section : split) {
            if(section.isEmpty())
                continue;

            if(section.matches(FORMATTER_REGEX)) {
                if(!lastFormat)
                    styleStack.push();
                TextFormatterRegistry.tryApply(styleStack, section.substring(2, section.length()-1));
                lastFormat = true;
            }
            else {
                paragraph.add(Component.literal(section).withStyle(styleStack.peek()));
                lastFormat = false;
            }
        }

        return getChunksFrom(paragraph, lineWidth, lineHeight);
    }

    private static List<TextChunk> getChunksFrom(List<Component> sections, int lineWidth, int lineHeight) {
        Font font = Minecraft.getInstance().font;
        int x = 0;
        int line = 0;

        List<TextChunk> chunks = new ArrayList<>();
        for(Component section : sections) {
            while(true) {
                Pair<Component, Component> pair = tryWrapComponent(section, lineWidth-x, lineWidth);

                if(pair.getFirst() != null && !pair.getFirst().getString().isEmpty()) {
                    int width = font.width(pair.getFirst()); // First add component to current line-- we know this one is pre-linebreak
                    chunks.add(new TextChunk(pair.getFirst(), x, line*lineHeight, width, lineHeight));
                    x += width;
                }

                if(pair.getSecond() == null) // If no linebreak is present we've reached the end of the section.
                    break;
                else {
                    line++; // If linebreak present, start new line and attempt another wrap.
                    x = 0;
                    section = pair.getSecond();
                }
            }

        }
        return chunks;
    }

    /**
     * Similar to font split, but only splits one time, so we can use variable lengths.
     */
    private static Pair<Component, Component> tryWrapComponent(Component text, int maxWidth, int lineWidth) {
        Font font = Minecraft.getInstance().font;
        StringSplitter splitter = font.getSplitter();

        String rawString = text.getString();
        int lineBreak = splitter.findLineBreak(text.getString(), maxWidth, text.getStyle());
        if(lineBreak == rawString.length()) // If break is end of section, we don't need to wrap.
            return Pair.of(text, null);

        int firstSpace = rawString.indexOf(" "); // For single word sequences OR breaks from a newline
        if((firstSpace == -1 || lineBreak < firstSpace) && rawString.charAt(lineBreak) != '\n') { // If we follow word-split rules for \n we cause an infinite loop
            if(font.width(text) > lineWidth)
                // If the single word section is longer than an entire line, we split it and add a -
                return Pair.of(Component.literal(rawString.substring(0, lineBreak-1) + "-").withStyle(text.getStyle()),
                        Component.literal(rawString.substring(lineBreak)).withStyle(text.getStyle()));
            else
                return Pair.of(null, text); // For short single word sections, return it on the next line.
        }

        return Pair.of(
                Component.literal(rawString.substring(0, lineBreak)).withStyle(text.getStyle()),
                Component.literal(rawString.substring(lineBreak+1)).withStyle(text.getStyle())
        );
    }

}
