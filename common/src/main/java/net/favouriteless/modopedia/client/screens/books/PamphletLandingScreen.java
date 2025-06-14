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
import net.minecraft.client.Minecraft;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.List;

public class PamphletLandingScreen extends ButtonListScreen {

    public static final int TITLE_COLOUR = 0xEFE732;

    protected final Category category;
    protected final Component subtitle;

    public PamphletLandingScreen(Book book, String langCode, LocalisedBookContent content, Category category, BookScreen lastScreen) {
        super(book, langCode, content, Component.translatable(book.getTitle()).withStyle(Style.EMPTY.withColor(TITLE_COLOUR)), lastScreen,
                Component.translatable("screen.modopedia.entries").withStyle(Style.EMPTY.withColor(book.getHeaderColour())),
                List.of(
                        () -> category.getChildren().stream().filter(content::hasCategory).toList(),
                        () -> category.getEntries().stream().filter(content::hasEntry).toList()
                ),
                List.of(
                        CategoryScreen::createCategoryButton,
                        CategoryScreen::createEntryButton
                )
        );
        this.category = category;
        this.subtitle = book.getSubtitle() != null ? Component.translatable(book.getSubtitle()).withStyle(Style.EMPTY.withColor(TITLE_COLOUR).withFont(Modopedia.id("default"))) : null;
    }

    public PamphletLandingScreen(Book book, String langCode, LocalisedBookContent content, Category category) {
        this(book, langCode, content, category, null);
    }

    @Override
    protected ScreenPage initFirstPage() {
        String rawLandingText = book.getRawLandingText();
        if(rawLandingText != null)
            rawLandingText = Language.getInstance().getOrDefault(rawLandingText);

        List<TextChunk> landingText = TextParser.parse(rawLandingText, texture.pages().getFirst().width(),
                Minecraft.getInstance().font.lineHeight, Justify.LEFT, getStyle());

        return new LandingScreenPage(this, title, subtitle, 37, 7, 10, landingText, 0, 0);
    }

    @Override
    protected boolean isTopLevel() {
        return true;
    }

}