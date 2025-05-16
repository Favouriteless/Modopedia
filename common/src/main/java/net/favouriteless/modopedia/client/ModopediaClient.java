package net.favouriteless.modopedia.client;

import net.favouriteless.modopedia.api.BookContentManager;
import net.favouriteless.modopedia.api.BookRegistry;
import net.favouriteless.modopedia.api.BookTypes;
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

        BookType type = BookTypes.get().getType(book.getType());
        if(type == null)
            return;

        BookContent content = BookContentManager.get().getContent(id);
        if(content == null)
            return;

        String lang = Minecraft.getInstance().options.languageCode;
        LocalisedBookContent localContent = content.getLocalisedContent(lang);
        if(localContent == null)
            return;

        Screen screen = type.openLandingScreen(book, localContent);

        if(screen != null)
            Minecraft.getInstance().setScreen(screen);
    }

}
