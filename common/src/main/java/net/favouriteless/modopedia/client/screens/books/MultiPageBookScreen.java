package net.favouriteless.modopedia.client.screens.books;

import com.mojang.blaze3d.vertex.PoseStack;
import net.favouriteless.modopedia.api.ScreenCache;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.book.BookTexture.FixedRectangle;
import net.favouriteless.modopedia.api.book.BookTexture.Rectangle;
import net.favouriteless.modopedia.client.BookOpenHandler;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.ScreenPage;
import net.favouriteless.modopedia.client.screens.widgets.HoverableImageButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class MultiPageBookScreen extends BookScreen {

    private final List<ScreenPage> pages = new ArrayList<>();

    private int leftPage = 0;

    private HoverableImageButton leftButton;
    private HoverableImageButton rightButton;
    private HoverableImageButton backButton;

    public MultiPageBookScreen(Book book, String langCode, LocalisedBookContent content, BookScreen lastScreen, Component title) {
        super(book, langCode, content, lastScreen, title);
    }

    protected abstract void initPages(final Consumer<ScreenPage> pageConsumer);

    @Override
    protected void init() {
        super.init();
        pages.clear(); // Clear because init actually gets called whenever the window size changes too
        initPages(page -> {
            pages.add(page);
            for(AbstractWidget widget : page.getWidgets())
                addRenderableWidget(widget);
        });

        ResourceLocation tex = texture.location();
        int texWidth = texture.texWidth();
        int texHeight = texture.texHeight();

        FixedRectangle left = texture.left();
        FixedRectangle right = texture.right();
        FixedRectangle back = texture.back();

        leftButton = addRenderableWidget(new HoverableImageButton(tex, leftPos + left.x(), topPos + left.y(), left.width(), left.height(), left.u(), left.v(), texWidth, texHeight, b -> turnPageLeft(1)));
        rightButton = addRenderableWidget(new HoverableImageButton(tex, leftPos + right.x(), topPos + right.y(), right.width(), right.height(), right.u(), right.v(), texWidth, texHeight, b -> turnPageRight(1)));
        backButton = addRenderableWidget(new HoverableImageButton(tex, leftPos + back.x(), topPos + back.y(), back.width(), back.height(), back.u(), back.v(), texWidth, texHeight, b -> {
            if(hasShiftDown() || lastScreen == null) {
                ScreenCache.get().setLastScreen(bookId, langCode, null);
                BookOpenHandler.tryOpenBook(bookId);
            }
            else {
                minecraft.setScreen(lastScreen);
                ScreenCache.get().setLastScreen(bookId, langCode, lastScreen);
            }
        }));

        updateHiddenWidgets();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);

        PoseStack poseStack = graphics.pose();
        for(int i = 0; i < texture.pages().size() && leftPage+i < pages.size(); i++) {
            ScreenPage page = pages.get(leftPage + i);
            Rectangle details = texture.pages().get(i);

            int xShift = leftPos + details.u();
            int yShift = topPos + details.v();

            poseStack.pushPose();
            poseStack.translate(xShift, yShift, 0);
            page.render(graphics, poseStack, details, mouseX - xShift, mouseY - yShift, partialTick);
            poseStack.popPose();
        }
    }

    @Override
    public void tick() {
        super.tick();
        for(ScreenPage page : pages) {
            page.tick(this);
        }
    }

    protected void turnPageRight(int by) {
        int pageCount = texture.pages().size();
        if(leftPage + pageCount >= pages.size())
            return; // Do nothing if turning the page would just give a blank next screen.

        leftPage += pageCount;
        updateHiddenWidgets();
    }

    protected void turnPageLeft(int by) {
        int pageCount = texture.pages().size();
        if(leftPage - pageCount < 0)
            return; // Do nothing if turning the page would just give a blank next screen.

        leftPage -= pageCount;
        updateHiddenWidgets();
    }

    protected void updateHiddenWidgets() {
        for(int i = 0; i < pages.size(); i++) {
            if(i < leftPage || i >= leftPage + texture.pages().size()) // If not on current page spread
                pages.get(i).hideWidgets();
            else
                pages.get(i).showWidgets();
        }
        updateHiddenNavigation();
    }

    protected void updateHiddenNavigation() {
        if(leftPage == 0) {
            leftButton.active = false;
            leftButton.visible = false;
        }
        else {
            leftButton.active = true;
            leftButton.visible = true;
        }

        if(leftPage + texture.pages().size() >= pages.size()) {
            rightButton.active = false;
            rightButton.visible = false;
        }
        else {
            rightButton.active = true;
            rightButton.visible = true;
        }
    }

    protected int getPageCount() {
        return pages.size();
    }

    protected int getLeftPage() {
        return leftPage;
    }

    protected ScreenPage getPage(int index) {
        return pages.get(index);
    }

}
