package net.favouriteless.modopedia.client.page_components;

import net.favouriteless.modopedia.api.Lookup;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.book.page_components.PageComponent;
import net.favouriteless.modopedia.book.text.Justify;
import net.favouriteless.modopedia.book.text.TextChunk;
import net.favouriteless.modopedia.book.text.TextParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Style;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class TextPageComponent extends PageComponent {

    protected List<TextChunk> textChunks = new ArrayList<>();
    protected int width;
    protected int lineHeight;

    @Override
    public void init(Book book, Lookup lookup, Level level) {
        super.init(book, lookup, level);
        Justify justify = lookup.getOrDefault("justify", Justify.LEFT).as(Justify.class);

        width = lookup.getOrDefault("width", book.getLineWidth()).asInt();
        lineHeight = lookup.getOrDefault("line_height", Minecraft.getInstance().font.lineHeight).asInt();
        textChunks = TextParser.parse(lookup.get("text").asString(), width, lineHeight, justify, Style.EMPTY.withFont(book.getFont()).withColor(book.getTextColour()));
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTicks) {
        for(TextChunk word : textChunks) {
            word.render(graphics, x, y, mouseX - x, mouseY - y);
        }
    }

    @Override
    public boolean mouseClicked(BookRenderContext context, double mouseX, double mouseY, int button) {
        for(TextChunk chunk : textChunks) {
            if(chunk.mouseClicked(context, mouseX - x, mouseY - y, button))
                return true;
        }
        return false;
    }

}