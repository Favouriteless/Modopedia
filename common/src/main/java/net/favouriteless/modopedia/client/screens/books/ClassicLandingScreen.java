package net.favouriteless.modopedia.client.screens.books;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.books.BookTexture.Rectangle;
import net.favouriteless.modopedia.api.books.Category;
import net.favouriteless.modopedia.book.text.Justify;
import net.favouriteless.modopedia.book.text.TextChunk;
import net.favouriteless.modopedia.book.text.TextParser;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.BlankScreenPage;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.LandingScreenPage;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.ScreenPage;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.TitledScreenPage;
import net.favouriteless.modopedia.client.screens.widgets.ItemTextButton;
import net.minecraft.client.Minecraft;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.List;
import java.util.function.Consumer;

public class ClassicLandingScreen extends MultiPageBookScreen {

    protected final Component subtitle;

    public ClassicLandingScreen(Book book, String langCode, LocalisedBookContent content, BookScreen lastScreen) {
        super(book, langCode, content, lastScreen, Component.translatable(book.getTitle()).withStyle(Style.EMPTY.withColor(0xEFE732)));
        this.subtitle = book.getSubtitle() != null ? Component.translatable(book.getSubtitle()).withStyle(Style.EMPTY.withColor(0xEFE732).withFont(Modopedia.id("default"))) : null;
    }

    public ClassicLandingScreen(Book book, String langCode, LocalisedBookContent content) {
        this(book, langCode, content, null);
    }

    @Override
    protected void initPages(final Consumer<ScreenPage> pageConsumer) {
        Minecraft mc = Minecraft.getInstance();

        String rawLandingText = book.getRawLandingText();
        if(rawLandingText != null)
            rawLandingText = Language.getInstance().getOrDefault(rawLandingText);

        List<TextChunk> landingText = TextParser.parse(rawLandingText, texture.pages().getFirst().width(), mc.font.lineHeight,
                Justify.LEFT, Style.EMPTY.withFont(book.getFont()).withColor(book.getTextColour())
        );

        pageConsumer.accept(new LandingScreenPage(this, title, subtitle, 37, 7, 10, landingText, 0, 0));

        if(content == null)
            return;

        List<String> categories = content.getCategoryIds().stream()
                .filter(c -> {
                    Category cat = content.getCategory(c);
                    return cat != null && cat.getDisplayOnFrontPage();
                })
                .toList();

        final int spacing = ItemTextButton.SIZE+1;
        Component header = Component.translatable("screen.modopedia.categories");

        int startIndex = 0;
        while(startIndex < categories.size()) {
            int pageCount = getPageCount();
            Rectangle details = texture.pages().get(pageCount % texture.pages().size());

            ScreenPage page;
            int yStart = details.y();

            if(pageCount == 1) {
                yStart += minecraft.font.lineHeight + texture.separator().height() + 3;
                page = new TitledScreenPage(this, header, details.width()/2 - minecraft.font.width(header)/2, 0);
            }
            else {
                page = new BlankScreenPage(this);
            }

            int onPage = (details.height() - yStart) / spacing;

            ItemTextButton.createItemTextButtons(categories.subList(startIndex, Math.min(startIndex+onPage, categories.size())), details.x(), yStart, (id, x, y) -> {
                Category cat = content.getCategory(id);
                return new ItemTextButton(leftPos + x, topPos + y, details.width(), cat.getIcon(),
                        Component.literal(cat.getTitle()).withStyle(getStyle()), b -> minecraft.setScreen(new CategoryScreen(book, langCode, content, cat, this)));
            }).forEach(page::addWidget);

            pageConsumer.accept(page);
            startIndex += onPage;
        }
    }

}
