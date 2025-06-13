package net.favouriteless.modopedia.book.registries.client;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.BookScreenFactory;
import net.favouriteless.modopedia.api.book.BookType;
import net.favouriteless.modopedia.api.book.BookType.Type;
import net.favouriteless.modopedia.api.registries.client.BookScreenFactoryRegistry;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class BookScreenFactoryRegistryImpl implements BookScreenFactoryRegistry {

    public static final BookScreenFactoryRegistryImpl INSTANCE = new BookScreenFactoryRegistryImpl();

    private final Map<ResourceLocation, BookScreenFactory<?>> factories = new HashMap<>();

    private BookScreenFactoryRegistryImpl() {}

    @Override
    public <T extends BookType> void register(Type<T> type, BookScreenFactory<T> factory) {
        if(factories.containsKey(type.id()))
            Modopedia.LOG.error("Attempted to register duplicate book screen factory: {}", type.id());
        else
            factories.put(type.id(), factory);
    }

    @Override
    @SuppressWarnings("unchecked") // This is only "unchecked"-- you can't register something which doesn't work.
    public @Nullable <T extends BookType> BookScreenFactory<T> get(T type) {
        return (BookScreenFactory<T>)factories.get(type.type().id());
    }

}
