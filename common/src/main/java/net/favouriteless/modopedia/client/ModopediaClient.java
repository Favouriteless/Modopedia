package net.favouriteless.modopedia.client;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.client.reload_listeners.BookContentReloadListener;
import net.favouriteless.modopedia.common.reload_listeners.TemplateReloadListener;
import net.favouriteless.modopedia.platform.ClientServices;

public class ModopediaClient {

    public static void init() {
        ClientServices.CLIENT_REGISTRY.registerReloadListener(Modopedia.id("templates"),
                new TemplateReloadListener(Modopedia.BOOK_DIRECTORY + "/templates"));
        ClientServices.CLIENT_REGISTRY.registerReloadListener(Modopedia.id("book_content"),
                new BookContentReloadListener(Modopedia.BOOK_DIRECTORY));
    }

}
