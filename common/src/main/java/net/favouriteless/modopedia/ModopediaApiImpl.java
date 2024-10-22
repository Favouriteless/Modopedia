package net.favouriteless.modopedia;

import net.favouriteless.modopedia.api.BookRegistry;
import net.favouriteless.modopedia.api.ModopediaApi;
import net.favouriteless.modopedia.api.books.Book;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class ModopediaApiImpl implements ModopediaApi {

    public static final ModopediaApiImpl INSTANCE = new ModopediaApiImpl();

    private ModopediaApiImpl() {}

    @Override
    public boolean bookContentsLoaded() {
        return false;
    }

    @Nullable
    @Override
    public Book getBook(ResourceLocation id) {
        return BookRegistry.get().getBook(id);
    }

}
