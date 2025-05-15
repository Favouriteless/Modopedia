package net.favouriteless.modopedia.client.screens.books;

import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.books.BookTexture.Dimensions;
import net.favouriteless.modopedia.api.books.Category;
import net.favouriteless.modopedia.book.text.TextChunk;
import net.favouriteless.modopedia.book.text.TextParser;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.BlankScreenPage;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.ScreenPage;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.TitledScreenPage;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.TitledTextScreenPage;
import net.favouriteless.modopedia.client.screens.widgets.ItemTextButton;
import net.minecraft.client.Minecraft;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.List;
import java.util.function.Consumer;

public class ClassicLandingScreen extends MultiPageBookScreen {

    protected final Component subtitle;

    public ClassicLandingScreen(Book book, LocalisedBookContent content, BookScreen lastScreen) {
        super(book, content, lastScreen, Component.translatable(book.getTitle()));
        this.subtitle = book.getSubtitle() != null ? Component.translatable(book.getSubtitle()) : null;
    }

    public ClassicLandingScreen(Book book, LocalisedBookContent content) {
        this(book, content, null);
    }

    @Override
    protected void initPages(final Consumer<ScreenPage> pageConsumer) {
        Minecraft mc = Minecraft.getInstance();

        String rawLandingText = book.getRawLandingText();
        if(rawLandingText != null)
            rawLandingText = Language.getInstance().getOrDefault(rawLandingText);

        List<TextChunk> landingText = TextParser.parse(rawLandingText, texture.pages().getFirst().width(), mc.font.lineHeight,
                Style.EMPTY.withFont(book.getFont()).withColor(book.getTextColour())
        );

        int titleX = texture.pages().getFirst().width()/2 - mc.font.width(title)/2;
        int landingTextY = mc.font.lineHeight + 2;

        pageConsumer.accept(new TitledTextScreenPage(this, title, titleX, 0, landingText, 0, landingTextY));

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
            int dimIndex = pageCount % texture.pages().size();
            Dimensions dims = texture.pages().get(dimIndex);

            ScreenPage page;
            int yStart = dims.y();

            if(pageCount == 1) {
                yStart += minecraft.font.lineHeight + 2;
                page = new TitledScreenPage(this, header, dims.width()/2 - minecraft.font.width(header)/2, 0);
            }
            else {
                page = new BlankScreenPage(this);
            }

            int onPage = (dims.height() - yStart) / spacing;

            ItemTextButton.createItemTextButtons(categories.subList(startIndex, Math.min(startIndex+onPage, categories.size())), dims.x(), yStart, (id, x, y) -> {
                Category cat = content.getCategory(id);
                return new ItemTextButton(leftPos + x, topPos + y, dims.width(), cat.getIcon(),
                        Component.literal(cat.getTitle()).withStyle(getStyle()), b -> minecraft.setScreen(new CategoryScreen(book, content, cat, this)));
            }).forEach(page::addWidget);

            pageConsumer.accept(page);
            startIndex += onPage;
        }
    }

}
