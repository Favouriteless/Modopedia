package net.favouriteless.modopedia.client.book_types;

import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookContent;
import net.favouriteless.modopedia.api.books.BookType;
import net.favouriteless.modopedia.client.screens.books.classic.ClassicLandingScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

public class ClassicBookType implements BookType {

    @Override
    public Screen openLandingScreen(Book book, BookContent content) {
        String lang = Minecraft.getInstance().getLanguageManager().getSelected();
        return new ClassicLandingScreen(book, content.getLocalisedContent(lang));
    }

    @Override
    public Screen openCategoryScreen(Book book, BookContent content, ResourceLocation category) {
        throw new UnsupportedOperationException("Feature is not complete.");
    }

    @Override
    public Screen openEntryScreen(Book book, BookContent content, ResourceLocation entry) {
        throw new UnsupportedOperationException("Feature is not complete.");
    }

}
