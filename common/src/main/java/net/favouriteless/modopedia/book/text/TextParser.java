package net.favouriteless.modopedia.book.text;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.ArrayList;
import java.util.List;

public class TextParser {

    public static final String FORMATTER_REGEX = "\\$\\([^$()]*\\)";

    public static List<Word> parse(String rawText, int lineWidth, int lineHeight) {
        String[] split = rawText.splitWithDelimiters(FORMATTER_REGEX, 0); // Separate actual text and formatters

        TextState state = new TextState(Style.EMPTY.withFont(Style.DEFAULT_FONT));
        List<String> chunks = new ArrayList<>();

        for(String chunk : split) {
            if(chunk.matches(FORMATTER_REGEX)) {
                state.push();
                TextFormatterRegistry.tryApply(state, chunk.substring(2, chunk.length()-1));
            }
            else {
                if(!chunk.isEmpty())
                    chunks.add(chunk);
            }
        }

        return getChunksFrom(state, chunks, lineWidth, lineHeight);
    }

    private static List<Word> getChunksFrom(TextState state, List<String> chunks, int lineWidth, int lineHeight) {
        List<List<String>> groups = new ArrayList<>();
        for(String chunk : chunks)
            groups.add(List.of(chunk.split(" "))); // Split the chunks into words.


        List<Word> out = new ArrayList<>();
        Font font = Minecraft.getInstance().font;
        int space = font.width(Component.literal(" ").withStyle(state.getBaseStyle()));
        int x = 0;
        int line = 0;

        for(List<String> group : groups) {
            for(String chunk : group) {
                Component component = Component.literal(chunk).withStyle(state.peek());
                int width = font.width(component);

                if((x + width + space) > lineWidth) {
                    line++;
                    x = 0;
                }

                out.add(new Word(component, x, line*lineHeight, width, lineHeight));
                x += width + space;
            }
            state.pop();
        }

        return out;
    }

}
