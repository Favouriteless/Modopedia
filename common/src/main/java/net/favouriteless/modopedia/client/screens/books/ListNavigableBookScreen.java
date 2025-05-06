package net.favouriteless.modopedia.client.screens.books;

import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.books.BookTexture.PageDetails;
import net.favouriteless.modopedia.client.screens.BookScreen;
import net.favouriteless.modopedia.client.screens.widgets.ItemTextButton;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ListNavigableBookScreen extends BookScreen {

    protected final List<ItemTextButton[]> buttons = new ArrayList<>();

    public ListNavigableBookScreen(Book book, LocalisedBookContent content, BookScreen lastScreen) {
        super(book, content, lastScreen);
    }

    protected void initButtonList(int startIndex, Collection<String> ids, ItemTextButtonFactory factory) {
        int size = minecraft.font.lineHeight + 3;
        int perPage = getListHeight() / size;

        for(int i = 0; i < Mth.ceil(content.getCategoryIds().size() / (float)perPage); i++)
            buttons.add(new ItemTextButton[perPage]);

        int index = startIndex;
        for(String id : ids) {
            PageDetails page = texture.pages().get(index / perPage + 1);

            int pageIndex = index % perPage;
            int x = leftPos + page.x();
            int y = topPos + page.y() + minecraft.font.lineHeight + 2 + size * pageIndex; // lineHeight here is for the title

            ItemTextButton button = factory.create(id, x, y, page.width());

            buttons.get(index / perPage)[pageIndex] = button;
            addRenderableWidget(button);
            index++;
        }
    }

    /**
     * @return The height of the shortest page on this screen's BookTexture, not including the first page. Relevant for
     * side scrolling behaviour.
     */
    protected int getListHeight() {
        int smallest = Integer.MAX_VALUE;
        for(int i = 1; i < texture.pages().size(); i++) {
            PageDetails page = texture.pages().get(i);
            if(page.height() < smallest)
                smallest = page.height();
        }
        return smallest;
    }

    @FunctionalInterface
    protected interface ItemTextButtonFactory {

        ItemTextButton create(String id, int x, int y, int width);

    }

}
