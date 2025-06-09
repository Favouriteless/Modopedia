package net.favouriteless.modopedia.client.screens.books;

import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.books.BookTexture.Rectangle;
import net.favouriteless.modopedia.api.books.Category;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.ScreenPage;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.TitledTextScreenPage;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.List;

public class CategoryScreen extends ButtonListScreen {

    protected final Category category;

    public CategoryScreen(Book book, String langCode, LocalisedBookContent content, Category category, BookScreen lastScreen) {
        super(book, langCode, content, Component.literal(category.getTitle()), lastScreen,
                Component.translatable("screen.modopedia.entries").withStyle(Style.EMPTY.withColor(book.getHeaderColour())),
                List.of(
                        () -> category.getChildren().stream().filter(content::hasCategory).toList(),
                        () -> category.getEntries().stream().filter(content::hasEntry).toList()
                ),
                List.of(
                    CategoryScreen::createCategoryButton,
                    CategoryScreen::createEntryButton
                ));
        this.category = category;
    }

    public CategoryScreen(Book book, String langCode, LocalisedBookContent content, Category category) {
        this(book, langCode, content, category, null);
    }

    @Override
    protected ScreenPage initFirstPage() {
        int y = Minecraft.getInstance().font.lineHeight + 3;

        Rectangle sep = getBookTexture().widgets().get("separator");
        if(sep != null)
            y += sep.height();

        return new TitledTextScreenPage(this, title, category.getLandingText(), y, texture.pages().getFirst());
    }

}
