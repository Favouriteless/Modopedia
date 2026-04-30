package net.favouriteless.modopedia.client.screens.books;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.book.BookType;
import net.favouriteless.modopedia.api.book.Category;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.LandingScreenPage;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.ScreenPage;
import net.favouriteless.modopedia.common.book_types.LockedViewProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class PamphletLandingScreen<T extends BookType & LockedViewProvider> extends CategoryScreen<T> {

    public static final int TITLE_COLOUR = 0xEFE732;

    protected final Component subtitle;

    public PamphletLandingScreen(Book book, T type, String language, LocalisedBookContent content, Category category, BookScreen<?> lastScreen) {
        super(book, type, language, content, category, Component.translatable(book.getTitle()).withStyle(Style.EMPTY.withColor(TITLE_COLOUR)), lastScreen);
        this.subtitle = book.getSubtitle() != null ? Component.translatable(book.getSubtitle()).withStyle(Style.EMPTY.withColor(TITLE_COLOUR).withFont(Modopedia.id("default"))) : null;
    }

    @Override
    protected ScreenPage initFirstPage() {
        return LandingScreenPage.create(this, subtitle, language);
    }

    @Override
    protected boolean isTopLevel() {
        return true;
    }

}