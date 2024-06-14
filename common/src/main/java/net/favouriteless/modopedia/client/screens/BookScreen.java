package net.favouriteless.modopedia.client.screens;

import net.favouriteless.modopedia.api.BookRenderContext;
import net.favouriteless.modopedia.api.books.Book;
import net.minecraft.client.gui.screens.Screen;

public abstract class BookScreen extends Screen implements BookRenderContext {

    public static final int FULL_WIDTH = 271;
    public static final int FULL_HEIGHT = 180;

    private final Book book;

    protected BookScreen(Book book) {
        super(book.getTitle());
        this.book = book;
    }

}
