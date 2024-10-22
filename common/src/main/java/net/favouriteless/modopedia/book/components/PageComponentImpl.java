package net.favouriteless.modopedia.book.components;

import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.PageComponent;

public abstract class PageComponentImpl implements PageComponent {

    protected Book book;
    protected int x;
    protected int y;
    protected int pageNum;

    @Override
    public void init(Book book, int x, int y, int pageNum) {
        this.book = book;
        this.x = x;
        this.y = y;
        this.pageNum = pageNum;
    }

}
