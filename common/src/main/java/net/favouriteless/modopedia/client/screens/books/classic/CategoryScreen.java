package net.favouriteless.modopedia.client.screens.books.classic;

import com.mojang.blaze3d.vertex.PoseStack;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.books.BookTexture.PageDetails;
import net.favouriteless.modopedia.api.books.Category;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.client.screens.BookScreen;
import net.favouriteless.modopedia.client.screens.widgets.ItemTextButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.List;

public class CategoryScreen extends BookScreen {

    protected final Category category;

    protected int entryPage = 0;
    protected final List<List<ItemTextButton>> entryButtons = new ArrayList<>();

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

        // If tex only has more than one page we'll assume they're all the same size as the 2nd, the 1st is reserved for
        // the landing text and title.
        PageDetails page = texture.pages().size() > 1 ? texture.pages().get(1) : texture.pages().getFirst();

        int size = minecraft.font.lineHeight + 3;
        int perPage = page.height() / (minecraft.font.lineHeight + 3);

        for(int i = 0; i < Mth.ceil(content.getEntryIds().size() / (float)perPage); i++)
            entryButtons.add(new ArrayList<>());

        int count = 0;
        for(String id : content.getEntryIds()) {
            Entry entry = content.getEntry(id);
            int y = page.y() + minecraft.font.lineHeight + 2 + size * (count % perPage);

            ItemTextButton button = new ItemTextButton(leftPos + page.x(), topPos + y, page.width(),
                    b -> minecraft.setScreen(new EntryScreen(book, content, entry, this)),
                    entry.getIcon(), Component.literal(entry.getTitle()).withStyle(Style.EMPTY.withFont(book.getFont())));

            entryButtons.get(count / perPage).add(button);
            addRenderableWidget(button);
            count++;
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        PoseStack poseStack = graphics.pose();

        renderTitlePage(graphics, poseStack, mouseX, mouseY, partialTick);

        for(int i = 1; i < texture.pages().size(); i++) {
            renderCategoriesPage(graphics, poseStack, mouseX, mouseY, partialTick, i);
        }
    }

    protected void renderTitlePage(GuiGraphics graphics, PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
//        PageDetails page = texture.pages().getFirst();
//
//        int xShift = leftPos + page.x(); // We shift our click positions and pose to be relative to the page
//        int yShift = topPos + page.y();
//
//        mouseX -= xShift;
//        mouseY -= yShift;
//
//        poseStack.pushPose();
//        poseStack.translate(xShift, yShift, 0);
//
//        poseStack.translate(0, 30, 0);
//        for(TextChunk chunk : landingText) {
//            chunk.render(graphics, Minecraft.getInstance().font, mouseX, mouseY);
//        }
//
//        poseStack.popPose();
    }

    protected void renderCategoriesPage(GuiGraphics graphics, PoseStack poseStack, int mouseX, int mouseY, float partialTick, int index) {
//        PageDetails page = texture.pages().get(index);
//
//        int xShift = leftPos + page.x(); // We shift our click positions and pose to be relative to the page
//        int yShift = topPos + page.y();
//
//        mouseX -= xShift;
//        mouseY -= yShift;
//
//
//        poseStack.pushPose();
//        poseStack.translate(xShift, yShift, 0); // Translate to page position.
//
//        renderCenteredHeader(graphics, poseStack, Component.translatable(Modopedia.translation("screen", "categories")), page.width());
//
//        poseStack.popPose();
    }

}
