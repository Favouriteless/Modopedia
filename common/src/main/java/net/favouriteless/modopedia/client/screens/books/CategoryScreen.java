package net.favouriteless.modopedia.client.screens.books;

import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.book.BookTexture.Rectangle;
import net.favouriteless.modopedia.api.book.Category;
import net.favouriteless.modopedia.api.book.Entry;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.ScreenPage;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.TitledTextPage;
import net.favouriteless.modopedia.common.book_types.LockedViewType;
import net.favouriteless.modopedia.util.ClientAdvancementHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.List;

public class CategoryScreen extends ButtonListScreen {

    protected final Category category;

    public CategoryScreen(Book book, String langCode, LocalisedBookContent content, Category category, LockedViewType lockedType, Component title, BookScreen lastScreen) {
        super(book, langCode, content, title, lastScreen, Component.translatable("screen.modopedia.entries").withStyle(Style.EMPTY.withColor(book.getHeaderColour())), lockedType,
                List.of(
                        () -> category.getChildren().stream().filter(c -> {
                            Category cat = content.getCategory(c);
                            return cat != null && (cat.getAdvancement() == null || lockedType == LockedViewType.TRANSLUCENT || ClientAdvancementHelper.hasAdvancement(cat.getAdvancement()));
                        }).toList(),
                        () -> category.getEntries().stream().filter(e -> {
                            Entry entry = content.getEntry(e);
                            return entry != null && (entry.getAdvancement() == null || lockedType == LockedViewType.TRANSLUCENT || ClientAdvancementHelper.hasAdvancement(entry.getAdvancement()));
                        }).toList()
                ),
                List.of(
                        CategoryScreen::createCategoryButton,
                        CategoryScreen::createEntryButton
                ));
        this.category = category;
    }

    public CategoryScreen(Book book, String langCode, LocalisedBookContent content, Category category, LockedViewType viewType, BookScreen lastScreen) {
        this(book, langCode, content, category, viewType, Component.literal(category.getTitle()), lastScreen);
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
