package net.favouriteless.modopedia.book.components;

import net.favouriteless.modopedia.api.books.PageComponent;

public abstract class PositionedPageComponent implements PageComponent {

    protected int x;
    protected int y;
    protected int pageNum;

    protected PositionedPageComponent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void init(int pageNum) {
        this.pageNum = pageNum;
    }

}
