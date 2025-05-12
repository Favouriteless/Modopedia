package net.favouriteless.modopedia.book.page_components;

import net.favouriteless.modopedia.api.Variable.Lookup;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.level.Level;

public class HeaderPageComponent extends PageComponent {

    protected Component header;
    protected boolean centered;
    protected int colour;

    @Override
    public void init(Book book, Lookup lookup, Level level) {
        super.init(book, lookup, level);

        Style style = Style.EMPTY
                .withFont(book.getFont())
                .withBold(lookup.getOrDefault("bold", false).asBoolean())
                .withUnderlined(lookup.getOrDefault("underline", false).asBoolean());

        header = Component.literal(lookup.get("text").asString()).withStyle(style);
        centered = lookup.getOrDefault("centered", true).asBoolean();
        colour = Integer.parseInt(lookup.getOrDefault("colour", 0).asString(), 16);
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTicks) {
        Font font = Minecraft.getInstance().font;
        graphics.drawString(font, header, centered ? x - font.width(header) / 2 : x, y, colour, false);
    }

}