package net.favouriteless.modopedia.client;

import net.favouriteless.modopedia.api.BookRegistry;
import net.favouriteless.modopedia.api.BookTypes;
import net.favouriteless.modopedia.api.books.Book;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

public class ModopediaClient {

    public static void init() {

    }

    public static void tryOpenBook(ResourceLocation id) {
        Book book = BookRegistry.get().getBook(id);
        if(book == null)
            return;

        Screen screen = BookTypes.get().openLandingScreen(id, book);

        if(screen != null)
            Minecraft.getInstance().setScreen(screen);
    }

}
