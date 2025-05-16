package net.favouriteless.modopedia.client;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.BookTypes;
import net.favouriteless.modopedia.api.books.BookType;
import net.favouriteless.modopedia.client.book_types.ClassicBookType;
import net.favouriteless.modopedia.client.book_types.PageBookType;
import net.favouriteless.modopedia.client.book_types.PamphletBookType;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class BookTypesImpl implements BookTypes {

    public static final BookTypes INSTANCE = new BookTypesImpl();

    private final Map<ResourceLocation, BookType> factories = new HashMap<>();

    private BookTypesImpl() {
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
