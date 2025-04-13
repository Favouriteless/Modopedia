package net.favouriteless.modopedia.client;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.BookContentManager;
import net.favouriteless.modopedia.api.BookTypeRegistry;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookContent;
import net.favouriteless.modopedia.api.books.BookType;
import net.favouriteless.modopedia.client.book_types.ClassicBookType;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class BookTypeRegistryImpl implements BookTypeRegistry {

    public static final BookTypeRegistry INSTANCE = new BookTypeRegistryImpl();

    private final Map<ResourceLocation, BookType> factories = new HashMap<>();

    private BookTypeRegistryImpl() {
        register(Modopedia.id("classic"), new ClassicBookType());
    }

    @Override
    public void register(ResourceLocation id, BookType factory) {
        if(factories.containsKey(id))
            Modopedia.LOG.error("Attempted to register duplicate book screen factory: {}", id);
        else
            factories.put(id, factory);
    }

    @Override
    public Screen openLandingScreen(ResourceLocation id, Book book) {
        BookType factory = factories.get(book.getType());
        if(factory == null)
            return null;

        BookContent content = BookContentManager.get().getContent(id);
        if(content == null)
            return null;

        return factory.openLandingScreen(book, content);
    }

}
