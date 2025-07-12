package net.favouriteless.modopedia.client.screens.books;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.book.BookType;
import net.favouriteless.modopedia.book.text.Justify;
import net.favouriteless.modopedia.book.text.TextChunk;
import net.favouriteless.modopedia.book.text.TextParser;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.LandingScreenPage;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.ScreenPage;
import net.favouriteless.modopedia.common.book_types.LockedViewProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.List;

public class ClassicLandingScreen<T extends BookType & LockedViewProvider> extends ButtonListScreen<T> {

    public static final int TITLE_COLOUR = 0xEFE732;

    protected final Component subtitle;

    public ClassicLandingScreen(Book book, T type, String language, LocalisedBookContent content, BookScreen<?> lastScreen) {
        super(book, type, language, content, Component.translatable(book.getTitle()).withStyle(Style.EMPTY.withColor(TITLE_COLOUR)), lastScreen,
                Component.translatable("screen.modopedia.categories").withStyle(Style.EMPTY.withColor(book.getHeaderColour())),
                () -> content.getCategoryIds().stream()
                        .filter(c -> content.hasCategory(c) && content.getCategory(c).getDisplayOnFrontPage())
                        .<Factory>map(id -> (s, x, y, width) -> createCategoryButton(s, id, x, y, width))
                        .toList()
        );
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