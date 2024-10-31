package net.favouriteless.modopedia.client;

import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookTypeRegistry;
import net.favouriteless.modopedia.client.screens.BookScreen;
import net.minecraft.client.Minecraft;

public class ModopediaClient {

    public static void init() {

    }

    public static void tryOpenBook(Book book) {
        if(book == null)
            return;

        BookScreen screen = BookTypeRegistry.get().getScreen(book);

        if(screen != null)
            Minecraft.getInstance().setScreen(screen);
    }

}
