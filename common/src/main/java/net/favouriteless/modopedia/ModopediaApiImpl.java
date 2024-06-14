package net.favouriteless.modopedia;

import net.favouriteless.modopedia.api.PageComponentRegistry;
import net.favouriteless.modopedia.api.ModopediaApi;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.books.TestBook;
import net.favouriteless.modopedia.common.PageComponentRegistryImpl;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class ModopediaApiImpl implements ModopediaApi {

    public static ModopediaApiImpl INSTANCE = new ModopediaApiImpl();

    private ModopediaApiImpl() {}

    @Override
    public PageComponentRegistry getComponentRegistry() {
        return PageComponentRegistryImpl.INSTANCE;
    }

    @Override
    public boolean booksLoaded() {
        return false;
    }

    @Nullable
    @Override
    public Book getBook(ResourceLocation location) {
        return new TestBook();
    }

}
