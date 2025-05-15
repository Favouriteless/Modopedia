package net.favouriteless.modopedia.client.screens.books;

import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.books.BookTexture.Dimensions;
import net.favouriteless.modopedia.api.books.Category;
import net.favouriteless.modopedia.api.books.Entry;
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

public class CategoryScreen extends MultiPageBookScreen {

    protected final Category category;

    public CategoryScreen(Book book, LocalisedBookContent content, Category category, BookScreen lastScreen) {
        super(book, content, lastScreen, Component.literal(category.getTitle()));
        this.category = category;
    }

    public CategoryScreen(Book book, LocalisedBookContent content, Category category) {
        this(book, content, category, null);
    }

    @Override
    protected void initPages(Consumer<ScreenPage> pageConsumer) {
        Minecraft mc = Minecraft.getInstance();

        String rawLandingText = category.getRawLandingText();
        if(rawLandingText != null)
            rawLandingText = Language.getInstance().getOrDefault(rawLandingText);

        List<TextChunk> landingText = TextParser.parse(rawLandingText, texture.pages().getFirst().width(), mc.font.lineHeight,
                Style.EMPTY.withFont(book.getFont()).withColor(book.getTextColour())
        );

        int titleX = texture.pages().getFirst().width()/2 - mc.font.width(title)/2;
        int landingTextY = mc.font.lineHeight + 2;

        pageConsumer.accept(new TitledTextScreenPage(this, title, titleX, 0, landingText, 0, landingTextY));

        final int spacing = ItemTextButton.SIZE+1;
        Component header = Component.translatable("screen.modopedia.entries");
        List<String> categories = category.getChildren().stream().filter(content::hasCategory).toList();
        List<String> entries = category.getEntries().stream().filter(content::hasEntry).toList();

        int startIndex = 0;
        while(startIndex < categories.size() + entries.size()) {
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

            if(startIndex < categories.size()) {
                int endIndex = Math.min(startIndex + onPage, categories.size());
                int diff = endIndex - startIndex;

                ItemTextButton.createItemTextButtons(categories.subList(startIndex, endIndex), dims.x(), yStart, (id, x, y) -> {
                    Category cat = content.getCategory(id);
                    return new ItemTextButton(leftPos + x, topPos + y, dims.width(), cat.getIcon(),
                            Component.literal(cat.getTitle()).withStyle(getStyle()), b -> minecraft.setScreen(new CategoryScreen(book, content, cat, this)));
                }).forEach(page::addWidget);

                yStart += diff * spacing;
                startIndex += diff;
                onPage -= diff;
            }

            int entriesStart = startIndex - categories.size();
            if(entriesStart < entries.size() && onPage > 0) {
                ItemTextButton.createItemTextButtons(entries.subList(entriesStart, Math.min(entriesStart+onPage, entries.size())), dims.x(), yStart, (id, x, y) -> {
                    Entry entry = content.getEntry(id);
                    return new ItemTextButton(leftPos + x, topPos + y, dims.width(), entry.getIcon(),
                            Component.literal(entry.getTitle()).withStyle(getStyle()), b -> minecraft.setScreen(new EntryScreen(book, content, entry, this)));
                }).forEach(page::addWidget);
                startIndex += onPage;
            }

            pageConsumer.accept(page);
        }
    }

}
