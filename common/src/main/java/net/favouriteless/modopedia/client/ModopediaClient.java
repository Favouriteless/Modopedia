package net.favouriteless.modopedia.client;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookTypeRegistry;
import net.favouriteless.modopedia.client.screens.BookScreen;
import net.favouriteless.modopedia.common.reload_listeners.TemplateReloadListener;
import net.favouriteless.modopedia.platform.ClientServices;
import net.minecraft.client.Minecraft;

public class ModopediaClient {

    public static void init() {
        ClientServices.CLIENT_REGISTRY.registerReloadListener(Modopedia.id("templates"),
                new TemplateReloadListener(Modopedia.BOOK_DIRECTORY + "/templates"));
//        ClientServices.CLIENT_REGISTRY.registerReloadListener(Modopedia.id("book_content"),
//                new BookContentReloadListener(Modopedia.BOOK_DIRECTORY));
    }

    public static void tryOpenBook(Book book) {
        if(book == null)
            return;

        BookScreen screen = BookTypeRegistry.get().getScreen(book);

        if(screen != null)
            Minecraft.getInstance().setScreen(screen);
    }

}
