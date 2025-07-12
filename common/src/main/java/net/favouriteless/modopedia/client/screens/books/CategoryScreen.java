package net.favouriteless.modopedia.client.screens.books;

import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.book.BookTexture.Rectangle;
import net.favouriteless.modopedia.api.book.BookType;
import net.favouriteless.modopedia.api.book.Category;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.ScreenPage;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.TitledTextPage;
import net.favouriteless.modopedia.common.book_types.LockedViewProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.stream.Stream;

public class CategoryScreen<T extends BookType & LockedViewProvider> extends ButtonListScreen<T> {

    protected final Category category;

    public CategoryScreen(Book book, T type, String language, LocalisedBookContent content, Category category, Component title, BookScreen<?> lastScreen) {
        super(book, type, language, content, title, lastScreen, Component.translatable("screen.modopedia.entries").withStyle(Style.EMPTY.withColor(book.getHeaderColour())),
                () -> Stream.<Factory>concat(
                        category.getChildren().stream().map(id -> (s, x, y, width) -> createCategoryButton(s, id, x, y, width)),
                        category.getEntries().stream().map(id -> (s, x, y, width) -> createEntryButton(s, id, x, y, width))
                ).toList()
        );
        this.category = category;
    }

    public CategoryScreen(Book book, T type, String language, LocalisedBookContent content, Category category, BookScreen<?> lastScreen) {
        this(book, type, language, content, category, Component.literal(category.getTitle()), lastScreen);
    }

    @Override
    protected ScreenPage initFirstPage() {
        int y = Minecraft.getInstance().font.lineHeight + 3;

        Rectangle sep = getBookTexture().widgets().get("separator");
        if(sep != null)
            y += sep.height();

        return new TitledTextPage(this, title, category.getLandingText(), y, texture.pages().getFirst());
    }

}
