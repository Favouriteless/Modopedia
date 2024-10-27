package net.favouriteless.modopedia.client.screens.classic;

import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.client.screens.BookScreen;
import net.minecraft.client.gui.GuiGraphics;

public class ClassicEntryScreen extends BookScreen {

    protected final Entry entry;

    public ClassicEntryScreen(Book book, Entry entry) {
        super(book);
        this.entry = entry;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);

        for(PageComponent component : entry.getPages().get(0).getComponents()) {
            component.render(graphics, this, mouseX, mouseY, partialTick);
        }
    }
}
