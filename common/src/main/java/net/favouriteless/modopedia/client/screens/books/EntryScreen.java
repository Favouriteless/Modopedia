package net.favouriteless.modopedia.client.screens.books;

import net.favouriteless.modopedia.api.ScreenCache;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.books.BookTexture.Rectangle;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.api.books.Page;
import net.favouriteless.modopedia.api.books.page_components.PageEventListener;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.EntryScreenPage;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.ScreenPage;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EntryScreen extends MultiPageBookScreen {

    protected final Entry entry;

    protected final List<PageEventListener> children = new ArrayList<>();
    protected boolean isDragging = false;
    protected PageEventListener focused = null;

    public EntryScreen(Book book, String langCode, LocalisedBookContent content, Entry entry, BookScreen lastScreen) {
        super(book, langCode, content, lastScreen, Component.literal(entry.getTitle()));
        this.entry = entry;
        if(lastScreen == null)
            ScreenCache.get().setLastScreen(bookId, langCode, null); // You could get stuck in the entry screen if we didn't do this.
    }

    public EntryScreen(Book book, String langCode, LocalisedBookContent content, Entry entry) {
        this(book, langCode, content, entry, null);
    }

    @Override
    protected void initPages(Consumer<ScreenPage> pageConsumer) {
        for(Page page : entry.getPages()) {
            Rectangle details = texture.pages().get(children.size() % texture.pages().size());
            EntryScreenPage screenPage = new EntryScreenPage(this, details, page);
            children.add(screenPage);
            pageConsumer.accept(screenPage);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int left = getLeftPage();
        int max = Math.min(left + texture.pages().size(), children.size());

        for(PageEventListener child : children.subList(left, max)) {
            if(child.mouseClicked(this, mouseX - leftPos, mouseY - topPos, button)) {
                focused = child;
                if(button == 0)
                    isDragging = true;
                return true;
            }
        }

        focused = null;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if(button == 0 && isDragging) { // On release, first we check if there was a focused widget.
            isDragging = false;
            if(focused != null)
                return focused.mouseReleased(this, mouseX, mouseY, button);
        }

        int left = getLeftPage();
        int max = Math.min(left + texture.pages().size(), children.size());

        for(PageEventListener child : children.subList(left, max)) {
            if(child.mouseReleased(this, mouseX - leftPos, mouseY - topPos, button))
                return true;
        }

        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if(focused != null && isDragging && button == 0 && focused.mouseDragged(this, mouseX, mouseY, button, dragX, dragY))
            return true;
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int left = getLeftPage();
        int max = Math.min(left + texture.pages().size(), children.size());

        for(PageEventListener child : children.subList(left, max)) {
            if(child.mouseScrolled(this, mouseX - leftPos, mouseY - topPos, scrollX, scrollY))
                return true;
        }

        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(focused != null && focused.keyPressed(this, keyCode, scanCode, modifiers))
            return true;
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if(focused != null && focused.keyReleased(this, keyCode, scanCode, modifiers))
            return true;
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if(focused != null && focused.charTyped(this, codePoint, modifiers))
            return true;
        return super.charTyped(codePoint, modifiers);
    }

}
