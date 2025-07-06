package net.favouriteless.modopedia.book.text;

import com.mojang.datafixers.util.Pair;
import net.favouriteless.modopedia.book.registries.client.TextFormatterRegistryImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;

import java.util.ArrayList;
import java.util.List;

public class TextParser {

    private static final String FORMATTER_REGEX = "\\$\\([^$()]*\\)";
    private static final List<String> HYPHEN_EXCLUDE_LANGUAGES = List.of("ja_jp", "ko_kr", "lzh", "zh_cn", "zh_hk", "zh_tw");

    public static List<TextChunk> parse(String rawText, Style baseStyle, int lineWidth, int lineHeight, String language, Justify justify) {
        return parse(rawText, baseStyle, lineWidth, lineHeight, !HYPHEN_EXCLUDE_LANGUAGES.contains(language), justify);
    }

    /**
     * Process a raw formatted string into a body of {@link TextChunk}s.
     *
     * @param rawText Raw text to be parsed.
     * @param lineWidth Maximum width of each line. Used for wrapping and word splitting.
     * @param lineHeight The spacing between each line.
     * @param justify Text justification style (left, center or right).
     * @param baseStyle The "base" style of the text. When the style is reset (e.g. via $() ), this style will be used.
     * @param hyphenate If true, split words (single words which are longer than one line) will be indicated via a hyphen.
     *
     * @return A {@link List} of {@link TextChunk}s positioned and formatted according to the raw text.
     */
    public static List<TextChunk> parse(String rawText, Style baseStyle, int lineWidth, int lineHeight, boolean hyphenate, Justify justify) {
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
                TextFormatterRegistryImpl.INSTANCE.tryApply(styleStack, section.substring(2, section.length()-1));
                lastFormat = true;
            }
            else {
                paragraph.add(Component.literal(section).withStyle(styleStack.peek()));
                lastFormat = false;
            }
        }

        return getChunksFrom(paragraph, lineWidth, lineHeight, justify, hyphenate);
    }

    private static List<TextChunk> getChunksFrom(List<Component> sections, int maxWidth, int lineHeight, Justify justify, boolean hyphenate) {
        Font font = Minecraft.getInstance().font;

        List<TextChunk> out = new ArrayList<>();
        List<TextChunk> line = new ArrayList<>();

        int x = 0;
        int y = 0;

        for(int i = 0; i < sections.size(); i++) {
            Component section = sections.get(i);
            while(section != null) {
                Pair<Component, Component> pair = tryWrapComponent(section, maxWidth-x, maxWidth, hyphenate);

                if(pair.getFirst() != null && !pair.getFirst().getString().isEmpty()) { // Add first component to current line
                    int width = font.width(pair.getFirst());
                    line.add(new TextChunk(pair.getFirst(), x, y, width, lineHeight));
                    x += width;
                }

                if(i < sections.size()-1 && pair.getSecond() == null) // The last section gets counted as a break regardless
                    break;

                // If linebreak present: justify, start new line and re-wrap the section.
                tryJustify(line, maxWidth, justify);
                x = 0;
                y += lineHeight;

                section = pair.getSecond();
                out.addAll(line);
                line.clear();
            }
        }

        for(Component section : sections) {

        }
        out.addAll(line); // Ensure final line actually gets added-- there is no break so it normally wouldn't be.
        return out;
    }

    /**
     * Similar to {@link Font#split(FormattedText, int)}, but only splits one time, so we can use variable lengths.
     */
    private static Pair<Component, Component> tryWrapComponent(Component text, int maxWidth, int lineWidth, boolean hyphenate) {
        Font font = Minecraft.getInstance().font;
        String rawString = text.getString();

        int lineBreak = font.getSplitter().findLineBreak(text.getString(), maxWidth, text.getStyle());
        if(lineBreak == rawString.length()) // If break is end of section, we don't need to wrap.
            return Pair.of(text, null);

        int firstSpace = rawString.indexOf(" "); // For single word sequences OR breaks from a newline
        if((firstSpace == -1 || lineBreak < firstSpace) && rawString.charAt(lineBreak) != '\n') { // If we follow word-split rules for \n we cause an infinite loop
            if(font.width(text) > lineWidth) { // If the single word section is longer than an entire line, we split it (and add hyphen if needed)
                if(hyphenate) {
                    return Pair.of(
                            Component.literal(rawString.substring(0, lineBreak-1) + "-").withStyle(text.getStyle()),
                            Component.literal(rawString.substring(lineBreak-1)).withStyle(text.getStyle())
                    );
                }

                return Pair.of(
                        Component.literal(rawString.substring(0, lineBreak)).withStyle(text.getStyle()),
                        Component.literal(rawString.substring(lineBreak)).withStyle(text.getStyle())
                );
            }
            return Pair.of(null, text); // For short single word sections, return it on the next line.
        }

        return Pair.of(
                Component.literal(rawString.substring(0, lineBreak)).withStyle(text.getStyle()),
                Component.literal(rawString.substring(lineBreak+1)).withStyle(text.getStyle())
        );
    }

    private static int getWidth(List<TextChunk> chunks) {
        if(chunks.isEmpty())
            return 0;
        if(chunks.size() == 1)
            return chunks.getFirst().width;

        TextChunk last = chunks.getLast();
        return (last.x + last.width) - chunks.getFirst().x;
    }

    private static void tryJustify(List<TextChunk> line, int maxWidth, Justify justify) {
        if(justify == Justify.LEFT)
            return; // Chunks are already left-justified by default

        int width = getWidth(line);
        int offset = justify == Justify.RIGHT ? maxWidth - width : maxWidth/2 - width/2;
        line.forEach(c -> c.x += offset);
    }

}
