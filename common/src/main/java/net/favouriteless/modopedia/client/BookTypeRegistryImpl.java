package net.favouriteless.modopedia.client;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.BookContentManager;
import net.favouriteless.modopedia.api.books.BookTypeRegistry;
import net.favouriteless.modopedia.client.screens.BookScreen;
import net.favouriteless.modopedia.client.screens.classic.ClassicEntryScreen;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class BookTypeRegistryImpl implements BookTypeRegistry {

    public static final BookTypeRegistry INSTANCE = new BookTypeRegistryImpl();

    private final Map<ResourceLocation, Function<Book, BookScreen>> factories = new HashMap<>();

    private BookTypeRegistryImpl() {
//        register(Modopedia.id("classic"), ClassicLandingScreen::new);
        register(Modopedia.id("classic"), book -> new ClassicEntryScreen(book, BookContentManager.get().getContent(Modopedia.id("testbook")).getEntry("test_entry_templated")));
    }

    @Override
    public void register(ResourceLocation id, Function<Book, BookScreen> factory) {
        if(factories.containsKey(id))
            Modopedia.LOG.error("Attempted to register duplicate book type: {}", id);
        else
            factories.put(id, factory);
    }

    @Override
    public BookScreen getScreen(Book book) {
        ResourceLocation id = book.getType();
        return factories.containsKey(id) ? factories.get(id).apply(book) : null;
    }

    @Override
    public Function<Book, BookScreen> getFactory(ResourceLocation id) {
        return factories.get(id);
    }

}
