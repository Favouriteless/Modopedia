package net.favouriteless.modopedia.book.page_components;

import net.favouriteless.modopedia.api.Variable.Lookup;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.book.text.TextParser;
import net.minecraft.network.chat.Style;
import net.minecraft.world.level.Level;

public class TextPageComponent extends PageComponent {

    protected String rawText;
    protected int width;
    protected int lineHeight;

    @Override
    public void init(Book book, Lookup lookup, Level level) {
        super.init(book, lookup, level);
        rawText = lookup.get("text").asString();
        width = lookup.getOrDefault("width", book.getLineWidth()).asInt();
        lineHeight = lookup.getOrDefault("line_height", 9).asInt();

        TextParser.parse(rawText, width, lineHeight, Style.EMPTY.withFont(book.getFont()).withColor(book.getTextColour()))
                .forEach(chunk -> {
                    if(chunk.getText().getStyle().getClickEvent() != null)
                        addRenderableWidget(chunk);
                    else
                        addRenderable(chunk);
                }); // Don't bother giving it event handling if it doesn't have a click event anyway.
    }

}
