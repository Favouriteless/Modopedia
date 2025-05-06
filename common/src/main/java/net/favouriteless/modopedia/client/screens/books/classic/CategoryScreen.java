package net.favouriteless.modopedia.client.screens.books.classic;

import com.mojang.blaze3d.vertex.PoseStack;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.books.BookTexture.PageDetails;
import net.favouriteless.modopedia.api.books.Category;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.book.text.TextChunk;
import net.favouriteless.modopedia.client.screens.BookScreen;
import net.favouriteless.modopedia.client.screens.books.ListNavigableBookScreen;
import net.favouriteless.modopedia.client.screens.widgets.ItemTextButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class CategoryScreen extends ListNavigableBookScreen {

    protected final Category category;

    public CategoryScreen(Book book, LocalisedBookContent content, Category category, BookScreen lastScreen) {
        super(book, content, lastScreen);
        this.category = category;
    }

    public CategoryScreen(Book book, LocalisedBookContent content, Category category) {
        this(book, content, category, null);
    }

    @Override
    protected void init() {
        super.init();
        initButtonList(0, category.getEntries(), (id, x, y, width) -> {
            Entry entry = content.getEntry(id);
            return new ItemTextButton(x, y, width, entry.getIcon(),
                    Component.literal(entry.getTitle()).withStyle(Style.EMPTY.withFont(book.getFont())),
                    b -> minecraft.setScreen(new EntryScreen(book, content, entry, this)));
        });
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        PoseStack poseStack = graphics.pose();

        renderTitlePage(graphics, poseStack, mouseX, mouseY, partialTick);

        for(int i = 1; i < texture.pages().size(); i++) {
            renderEntriesPage(graphics, poseStack, mouseX, mouseY, partialTick, i);
        }
    }

    protected void renderTitlePage(GuiGraphics graphics, PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        PageDetails page = texture.pages().getFirst();

        int xShift = leftPos + page.x(); // We shift our click positions and pose to be relative to the page
        int yShift = topPos + page.y();

        mouseX -= xShift;
        mouseY -= yShift;

        poseStack.pushPose();
        poseStack.translate(xShift, yShift, 0);

        renderCenteredHeader(graphics, poseStack, Component.literal(category.getTitle()), page.width());

        poseStack.translate(0, 13, 0);
        for(TextChunk chunk : category.getLandingText()) {
            chunk.render(graphics, minecraft.font, mouseX, mouseY);
        }

        poseStack.popPose();
    }

    protected void renderEntriesPage(GuiGraphics graphics, PoseStack poseStack, int mouseX, int mouseY, float partialTick, int index) {
        PageDetails page = texture.pages().get(index);

        poseStack.pushPose();

        poseStack.translate(leftPos + page.x(), topPos + page.y(), 0); // Translate to page position.
        renderCenteredHeader(graphics, poseStack, Component.translatable(Modopedia.translation("screen", "entries")), page.width());

        poseStack.popPose();
    }

}
