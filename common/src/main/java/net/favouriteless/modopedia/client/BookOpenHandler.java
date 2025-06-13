package net.favouriteless.modopedia.client;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.ScreenCache;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.books.BookScreenFactory;
import net.favouriteless.modopedia.api.books.BookType;
import net.favouriteless.modopedia.api.registries.BookContentRegistry;
import net.favouriteless.modopedia.api.registries.BookRegistry;
import net.favouriteless.modopedia.api.registries.BookScreenFactoryRegistry;
import net.favouriteless.modopedia.client.screens.books.BookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class BookOpenHandler {

    public static void tryOpenBook(ResourceLocation id) {
        Book book = BookRegistry.get().getBook(id);
        if(book == null)
            return;

        String lang = Minecraft.getInstance().options.languageCode;
        BookScreen last = ScreenCache.get().getLastScreen(id, lang);

        LocalisedBookContent content = BookContentRegistry.get().getContent(id, lang);
        if(content == null)
            return;

        BookType type = book.getType();
        openBookScreen(tryUseFactory(type, f -> last != null ? last : f.openLandingScreen(type, book, lang, content, null)));
    }

    public static void tryOpenCategory(ResourceLocation id, String category) {
        Book book = BookRegistry.get().getBook(id);
        if(book == null)
            return;

        String lang = Minecraft.getInstance().options.languageCode;
        BookScreen last = ScreenCache.get().getLastScreen(id, lang);

        LocalisedBookContent content = BookContentRegistry.get().getContent(id, lang);
        if(content == null)
            return;

        BookType type = book.getType();
        openBookScreen(tryUseFactory(type, f -> f.openCategoryScreen(type, book, lang, content, category, last)));
    }

    public static void tryOpenEntry(ResourceLocation id, String entry) {
        Book book = BookRegistry.get().getBook(id);
        if(book == null)
            return;

        String lang = Minecraft.getInstance().options.languageCode;
        BookScreen last = ScreenCache.get().getLastScreen(id, lang);

        LocalisedBookContent content = BookContentRegistry.get().getContent(id, lang);
        if(content == null)
            return;

        BookType type = book.getType();
        openBookScreen(tryUseFactory(type, f -> f.openEntryScreen(type, book, lang, content, entry, last)));
    }

    public static void tryOpenCategory(String category) {
        if(Minecraft.getInstance().screen instanceof BookScreen screen)
            tryOpenCategory(BookRegistry.get().getId(screen.getBook()), category);
    }

    public static void tryOpenEntry(String entry) {
        if(Minecraft.getInstance().screen instanceof BookScreen screen)
            tryOpenEntry(BookRegistry.get().getId(screen.getBook()), entry);
    }

    private static void openBookScreen(BookScreen screen) {
        if(screen != null)
            Minecraft.getInstance().setScreen(screen);
    }

    private static <T extends BookType> BookScreen tryUseFactory(T type, Function<BookScreenFactory<T>, BookScreen> user) {
        BookScreenFactory<T> factory = BookScreenFactoryRegistry.get().get(type);
        if(factory == null)
            Modopedia.LOG.error("Could not find a BookScreenFactory for {}", type.type().id());
        return factory != null ? user.apply(factory) : null;
    }

}
