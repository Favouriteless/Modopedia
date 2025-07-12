package net.favouriteless.modopedia.client.screens.books;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.book.BookTexture.Rectangle;
import net.favouriteless.modopedia.api.book.BookType;
import net.favouriteless.modopedia.api.book.Category;
import net.favouriteless.modopedia.api.book.Entry;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.BlankScreenPage;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.ScreenPage;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.TitledScreenPage;
import net.favouriteless.modopedia.client.screens.widgets.BookItemTextButton;
import net.favouriteless.modopedia.common.book_types.LockedViewProvider;
import net.favouriteless.modopedia.common.book_types.LockedViewType;
import net.favouriteless.modopedia.util.client.AdvancementHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class ButtonListScreen<T extends BookType & LockedViewProvider> extends MultiPageBookScreen<T> {

    private final Component listTitle;
    private final Supplier<List<Factory>> factories;

    public ButtonListScreen(Book book, T type, String language, LocalisedBookContent content, Component title, BookScreen<?> lastScreen,
                            Component listTitle, Supplier<List<Factory>> factories) {
        super(book, type, language, content, lastScreen, title);
        this.listTitle = listTitle;
        this.factories = factories;
    }

    protected abstract ScreenPage initFirstPage();

    @Override
    protected void initPages(final Consumer<ScreenPage> pageConsumer) {
        pageConsumer.accept(initFirstPage());
        if(content == null)
            return;

        List<Factory> factories = this.factories.get();
        if(factories.isEmpty())
            return;

        final int spacing = BookItemTextButton.SIZE+1;

        Rectangle rectangle = texture.pages().get(getPageCount() % texture.pages().size());
        ScreenPage page = new TitledScreenPage(this, listTitle, rectangle);
        int y = rectangle.v() + minecraft.font.lineHeight + 3;

        final Rectangle sep = texture.widgets().get("separator");
        if(sep != null)
            y += sep.height();

        for(Factory factory : factories) {
            int onPage = (rectangle.height() - (y - rectangle.v())) / spacing; // Max number of buttons on the current page.
            int count = page.getWidgets().size();

            if(count >= onPage) {
                pageConsumer.accept(page);

                page = new BlankScreenPage(this);
                rectangle = texture.pages().get(getPageCount() % texture.pages().size());
                y = rectangle.v();
                count = 0;
            }

            BookItemTextButton button = factory.create(this, leftPos + rectangle.u(), topPos + y + spacing*count, rectangle.width());
            if(button != null)
                page.addWidget(button);
            else
                Modopedia.LOG.warn("Tried to add a null button to a page. This is most likely due to a missing/incorrect category or entry.");
        }
        pageConsumer.accept(page);
    }

    protected static BookItemTextButton createCategoryButton(ButtonListScreen<?> screen, String id, int x, int y, int width) {
        Category cat = screen.content.getCategory(id);
        if(cat == null)
            return null;

        boolean hasAdvancement = cat.getAdvancement() == null || AdvancementHelper.hasAdvancement(cat.getAdvancement());
        if(!hasAdvancement && screen.type.lockedViewType() == LockedViewType.HIDDEN)
            return null;

        return new BookItemTextButton(x, y, width, cat.getIcon(), Component.literal(cat.getTitle()).withStyle(screen.getStyle()), screen.book.getFlipSound(), !hasAdvancement,
                b -> Minecraft.getInstance().setScreen(new CategoryScreen<>(screen.book, screen.type, screen.language, screen.content, cat, screen)));
    }

    protected static BookItemTextButton createEntryButton(ButtonListScreen<?> screen, String id, int x, int y, int width) {
        Entry entry = screen.content.getEntry(id);
        if(entry == null)
            return null;

        boolean hasAdvancement = entry.getAdvancement() == null || AdvancementHelper.hasAdvancement(entry.getAdvancement());
        if(!hasAdvancement && screen.type.lockedViewType() == LockedViewType.HIDDEN)
            return null;

        return new BookItemTextButton(x, y, width, entry.getIcon(), Component.literal(entry.getTitle()).withStyle(screen.getStyle()), screen.book.getFlipSound(), !hasAdvancement,
                b -> Minecraft.getInstance().setScreen(new EntryScreen<>(screen.book, screen.type, screen.language, screen.content, entry, screen)));
    }

    @FunctionalInterface
    public interface Factory {

        @Nullable BookItemTextButton create(ButtonListScreen<?> screen, int x, int y, int width);

    }

}
