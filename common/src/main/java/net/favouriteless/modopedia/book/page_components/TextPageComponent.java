package net.favouriteless.modopedia.book.page_components;

import net.favouriteless.modopedia.api.Variable.Lookup;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.book.text.TextChunk;
import net.favouriteless.modopedia.book.text.TextParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Style;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class TextPageComponent extends PageComponent {

    protected List<TextChunk> textChunks = new ArrayList<>();
    protected String rawText;
    protected int width;
    protected int lineHeight;

    @Override
    public void init(Book book, Lookup lookup, Level level) {
        super.init(book, lookup, level);
        rawText = lookup.get("text").asString();
        width = lookup.getOrDefault("width", book.getLineWidth()).asInt();
        lineHeight = lookup.getOrDefault("line_height", Minecraft.getInstance().font.lineHeight).asInt();

        textChunks = TextParser.parse(rawText, width, lineHeight, Style.EMPTY.withFont(book.getFont()).withColor(book.getTextColour()), x, y);
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTicks) {
        Font font = Minecraft.getInstance().font;
        for(TextChunk word : textChunks) {
            word.render(graphics, font, mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(BookRenderContext context, double mouseX, double mouseY, int button) {
        for(TextChunk chunk : textChunks) {
            if(chunk.mouseClicked(context, mouseX, mouseY, button))
                return true;
        }
        return false;
    }

}