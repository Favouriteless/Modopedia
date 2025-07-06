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
import net.favouriteless.modopedia.util.client.AdvancementHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.List;

public class ClassicLandingScreen extends ButtonListScreen {

    public static final int TITLE_COLOUR = 0xEFE732;

    protected final Component subtitle;

    public ClassicLandingScreen(Book book, String langCode, LocalisedBookContent content, LockedViewType viewType, BookScreen lastScreen) {
        super(book, langCode, content, Component.translatable(book.getTitle()).withStyle(Style.EMPTY.withColor(TITLE_COLOUR)), lastScreen,
                Component.translatable("screen.modopedia.categories").withStyle(Style.EMPTY.withColor(book.getHeaderColour())), viewType,
                List.of(
                        () -> content.getCategoryIds().stream().filter(c -> {
                            Category cat = content.getCategory(c);
                            return cat != null && cat.getDisplayOnFrontPage() && (cat.getAdvancement() == null || viewType == LockedViewType.TRANSLUCENT || AdvancementHelper.hasAdvancement(cat.getAdvancement()));
                        }).toList()
                ),
                List.of(
                        ClassicLandingScreen::createCategoryButton
                )
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