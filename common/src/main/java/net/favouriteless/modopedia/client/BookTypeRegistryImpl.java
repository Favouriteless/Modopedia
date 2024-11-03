package net.favouriteless.modopedia.client;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.BookContentManager;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookTypeRegistry;
import net.favouriteless.modopedia.api.books.PageDetails;
import net.favouriteless.modopedia.client.screens.BookScreen;
import net.favouriteless.modopedia.client.screens.classic.EntryScreen;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class BookTypeRegistryImpl implements BookTypeRegistry {

    public static final BookTypeRegistry INSTANCE = new BookTypeRegistryImpl();

    private final Map<ResourceLocation, Function<Book, BookScreen>> types = new HashMap<>();

    private BookTypeRegistryImpl() {
//        register(Modopedia.id("classic"), ClassicLandingScreen::new);
        register(Modopedia.id("classic"), book ->
                new EntryScreen(book,
                        BookContentManager.get().getContent(Modopedia.id("testbook")).getEntry("test_entry_templated", "en_us"),
                        PageDetails.of(24, 15, 100, 148),
                        PageDetails.of(147, 15, 100, 148))
        );
    }

    @Override
    public void register(ResourceLocation id, Function<Book, BookScreen> factory) {
        if(types.containsKey(id))
            Modopedia.LOG.error("Attempted to register duplicate book type: {}", id);
        else
            types.put(id, factory);
    }

    @Override
    public BookScreen getScreen(Book book) {
        Function<Book, BookScreen> factory = getFactory(book.getType());
        return factory != null ? factory.apply(book) : null;
    }

    @Override
    public Function<Book, BookScreen> getFactory(ResourceLocation id) {
        if(!types.containsKey(id))
            return null;
        return types.get(id);
    }

}
