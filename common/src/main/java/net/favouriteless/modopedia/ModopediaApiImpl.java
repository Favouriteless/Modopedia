package net.favouriteless.modopedia;

import net.favouriteless.modopedia.api.BookRegistry;
import net.favouriteless.modopedia.api.ModopediaApi;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookContent;
import net.favouriteless.modopedia.api.books.BookContentManager;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class ModopediaApiImpl implements ModopediaApi {

    public static final ModopediaApi INSTANCE = new ModopediaApiImpl();
    public static boolean isLoading = true;

    private ModopediaApiImpl() {}

    @Override
    public boolean bookContentsLoaded() {
        return !isLoading;
    }

    @Override
    public @Nullable Book getBook(ResourceLocation id) {
        return BookRegistry.get().getBook(id);
    }

    @Override
    public @Nullable BookContent getBookContent(ResourceLocation id) {
         return BookContentManager.get().getContent(id);
    }

}
