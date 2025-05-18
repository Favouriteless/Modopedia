package net.favouriteless.modopedia.client;

import net.favouriteless.modopedia.api.registries.BookContentRegistry;
import net.favouriteless.modopedia.api.registries.BookRegistry;
import net.favouriteless.modopedia.api.ScreenCache;
import net.favouriteless.modopedia.api.registries.BookTypeRegistry;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookContent;
import net.favouriteless.modopedia.api.books.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.books.BookType;
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

        BookType type = BookTypeRegistry.get().getType(book.getType());
        if(type == null)
            return;

        BookContent content = BookContentRegistry.get().getContent(id);
        if(content == null)
            return;

        String lang = Minecraft.getInstance().options.languageCode;
        LocalisedBookContent localContent = content.getContent(lang);
        if(localContent == null)
            return;

        Screen screen = ScreenCache.get().getLastScreen(id, lang);
        if(screen == null)
            screen = type.openLandingScreen(book, lang, localContent);

        if(screen != null)
            Minecraft.getInstance().setScreen(screen);
    }

}
