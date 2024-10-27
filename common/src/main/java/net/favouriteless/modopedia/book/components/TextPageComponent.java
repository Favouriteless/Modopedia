package net.favouriteless.modopedia.book.components;

import net.favouriteless.modopedia.api.Variable.Lookup;
import net.favouriteless.modopedia.api.books.BookRenderContext;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.book.text.TextParser;
import net.favouriteless.modopedia.book.text.Word;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class TextPageComponent extends PageComponent {

    protected List<Word> words = new ArrayList<>();
    protected String rawText;
    protected int width = 100;

    @Override
    public void init(Lookup lookup) {
        super.init(lookup);
        rawText = lookup.get("text").asString();
        width = lookup.getOrDefault("width", 100).asInt();
        words = TextParser.parse(rawText, width, 9);
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTicks) {
        for(Word word : words) {
            word.render(graphics, Minecraft.getInstance().font, mouseX, mouseY);
        }
    }

}
