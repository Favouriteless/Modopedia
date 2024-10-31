package net.favouriteless.modopedia.book.components;

import net.favouriteless.modopedia.api.Variable.Lookup;
import net.favouriteless.modopedia.api.books.BookRenderContext;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.book.text.TextParser;
import net.favouriteless.modopedia.book.text.TextChunk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.List;

public class TextPageComponent extends PageComponent {

    protected List<TextChunk> textChunks = new ArrayList<>();
    protected String rawText;
    protected int width;
    protected int lineHeight;

    @Override
    public void init(Lookup lookup) {
        super.init(lookup);
        rawText = lookup.get("text").asString();
        width = lookup.getOrDefault("width", 100).asInt();
        lineHeight = lookup.getOrDefault("line_height", 9).asInt();

        textChunks = TextParser.parse(rawText, width, lineHeight);
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTicks) {
        for(TextChunk word : textChunks) {
            word.render(graphics, context, Minecraft.getInstance().font, mouseX, mouseY);
        }
    }

    @Override
    public boolean pageClicked(BookRenderContext context, double mouseX, double mouseY, int button) {
        for(TextChunk chunk : textChunks) {
            if(chunk.pageClicked(context, mouseX, mouseY, button))
                return true;
        }
        return false;
    }

}
