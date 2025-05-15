package net.favouriteless.modopedia.client.book_types;

import net.favouriteless.modopedia.api.books.*;
import net.favouriteless.modopedia.api.books.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.client.screens.books.CategoryScreen;
import net.favouriteless.modopedia.client.screens.books.ClassicLandingScreen;
import net.favouriteless.modopedia.client.screens.books.EntryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class ClassicBookType implements BookType {

    @Override
    public Screen openLandingScreen(Book book, BookContent content) {
        LocalisedBookContent local = content.getLocalisedContent(Minecraft.getInstance().getLanguageManager().getSelected());
        if(local == null)
            return null;

        return new ClassicLandingScreen(book, local);
    }

    @Override
    public Screen openCategoryScreen(Book book, BookContent content, String category) {
        LocalisedBookContent local = content.getLocalisedContent(Minecraft.getInstance().getLanguageManager().getSelected());
        if(local == null)
            return null;

        Category cat = local.getCategory(category);
        if(cat == null)
            return null;

        return new CategoryScreen(book, local, cat);
    }

    @Override
    public Screen openEntryScreen(Book book, BookContent content, String entry) {
        LocalisedBookContent local = content.getLocalisedContent(Minecraft.getInstance().getLanguageManager().getSelected());
        if(local == null)
            return null;

        Entry ent = local.getEntry(entry);
        if(ent == null)
            return null;

        return new EntryScreen(book, local, ent);
    }

}
