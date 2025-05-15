package net.favouriteless.modopedia.api.books.page_components;

public abstract class AbstractPageWidget implements PageEventListener, PageRenderable {

    protected final int x;
    protected final int y;
    protected final int width;
    protected final int height;

    public boolean active = true;

    protected AbstractPageWidget(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean shouldRender() {
        return active;
    }
}
