package net.favouriteless.modopedia.api;

import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookType;
import net.favouriteless.modopedia.client.BookTypesImpl;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

public interface BookTypes {

    static BookTypes get() {
        return BookTypesImpl.INSTANCE;
    }

    /**
     * Register a new {@link BookType}. Duplicates are not allowed.
     */
    void register(ResourceLocation id, BookType factory);

    Screen openLandingScreen(ResourceLocation id, Book book);

}
