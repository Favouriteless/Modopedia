package net.favouriteless.modopedia.client;

import com.mojang.datafixers.util.Function5;
import net.favouriteless.modopedia.api.ScreenCache;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookContent;
import net.favouriteless.modopedia.api.books.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.books.BookType;
import net.favouriteless.modopedia.api.registries.BookContentRegistry;
import net.favouriteless.modopedia.api.registries.BookRegistry;
import net.favouriteless.modopedia.api.registries.BookTypeRegistry;
import net.favouriteless.modopedia.client.screens.books.BookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

public class BookOpenHandler {

    public static void tryOpenBook(ResourceLocation id) {
        tryOpenInternal(id, (type, book, lang, content, screen) -> screen != null ? screen : type.openLandingScreen(book, lang, content, null));
    }

    public static void tryOpenCategory(ResourceLocation id, String categoryId) {
        tryOpenInternal(id, (type, book, lang, content, lastScreen) -> type.openCategoryScreen(book, lang, content, categoryId, lastScreen));
    }

    public static void tryOpenEntry(ResourceLocation id, String entryId) {
        tryOpenInternal(id, (type, book, lang, content, lastScreen) -> type.openEntryScreen(book, lang, content, entryId, lastScreen));
    }

    private static void tryOpenInternal(ResourceLocation id, Function5<BookType, Book, String, LocalisedBookContent, BookScreen, BookScreen> opener) {
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

        Screen screen = opener.apply(type, book, lang, localContent, ScreenCache.get().getLastScreen(id, lang));

        if(screen != null)
            Minecraft.getInstance().setScreen(screen);
    }

}
