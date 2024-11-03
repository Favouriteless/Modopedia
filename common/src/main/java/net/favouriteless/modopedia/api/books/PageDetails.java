package net.favouriteless.modopedia.api.books;

public record PageDetails(int x, int y, int width, int height) {

    public static PageDetails of(int x, int y, int width, int height) {
        return new PageDetails(x, y, width, height);
    }

}
