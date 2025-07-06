package net.favouriteless.modopedia.client.screens.books;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.book.Category;
import net.favouriteless.modopedia.book.text.Justify;
import net.favouriteless.modopedia.book.text.TextChunk;
import net.favouriteless.modopedia.book.text.TextParser;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.LandingScreenPage;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.ScreenPage;
import net.favouriteless.modopedia.common.book_types.LockedViewType;
import net.minecraft.client.Minecraft;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.List;

public class PamphletLandingScreen extends CategoryScreen {

    public static final int TITLE_COLOUR = 0xEFE732;

    protected final Component subtitle;

    public PamphletLandingScreen(Book book, String language, LocalisedBookContent content, Category category, LockedViewType lockedType, BookScreen lastScreen) {
        super(book, language, content, category, lockedType, Component.translatable(book.getTitle()).withStyle(Style.EMPTY.withColor(TITLE_COLOUR)), lastScreen);
        this.subtitle = book.getSubtitle() != null ? Component.translatable(book.getSubtitle()).withStyle(Style.EMPTY.withColor(TITLE_COLOUR).withFont(Modopedia.id("default"))) : null;
    }

    @Override
    protected ScreenPage initFirstPage() {
        String rawLandingText = book.getRawLandingText();
        if(rawLandingText != null)
            rawLandingText = Language.getInstance().getOrDefault(rawLandingText);

        int lineWidth = texture.pages().getFirst().width();
        int lineHeight = Minecraft.getInstance().font.lineHeight;
        List<TextChunk> landingText = TextParser.parse(rawLandingText, getStyle(), lineWidth, lineHeight, language, Justify.LEFT);

        return new LandingScreenPage(this, title, subtitle, 37, 7, 10, landingText, 0, 0);
    }

    @Override
    protected boolean isTopLevel() {
        return true;
    }

}