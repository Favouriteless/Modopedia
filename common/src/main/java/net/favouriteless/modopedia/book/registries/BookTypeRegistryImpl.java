package net.favouriteless.modopedia.book.registries;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.registries.BookTypeRegistry;
import net.favouriteless.modopedia.api.books.BookType;
import net.favouriteless.modopedia.book.book_types.ClassicBookType;
import net.favouriteless.modopedia.book.book_types.PageBookType;
import net.favouriteless.modopedia.book.book_types.PamphletBookType;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class BookTypeRegistryImpl implements BookTypeRegistry {

    public static final BookTypeRegistryImpl INSTANCE = new BookTypeRegistryImpl();

    private final Map<ResourceLocation, BookType> factories = new HashMap<>();

    private BookTypeRegistryImpl() {
        register(Modopedia.id("classic"), new ClassicBookType());
        register(Modopedia.id("pamphlet"), new PamphletBookType());
        register(Modopedia.id("page"), new PageBookType());
    }

    @Override
    public void register(ResourceLocation id, BookType factory) {
        if(factories.containsKey(id))
            Modopedia.LOG.error("Attempted to register duplicate book screen factory: {}", id);
        else
            factories.put(id, factory);
    }

    @Override
    public BookType getType(ResourceLocation id) {
        return factories.get(id);
    }

}
