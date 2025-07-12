package net.favouriteless.modopedia.client.page_components;

import com.mojang.blaze3d.vertex.PoseStack;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.Lookup;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.book.page_components.ItemDisplay;
import net.favouriteless.modopedia.api.book.page_components.PageComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class ItemGalleryPageComponent extends PageComponent {

    public static final ResourceLocation ID = Modopedia.id("item_gallery");

    private ItemDisplay display;

    @Override
    public void init(Book book, Lookup lookup, Level level) {
        super.init(book, lookup, level);
        display = lookup.get("display").as(ItemDisplay.class);
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick) {
        PoseStack pose = graphics.pose();

        pose.pushPose();
        pose.translate(x, y, 0);
        display.render(graphics, context, mouseX - x, mouseY - y, entryId);
        pose.popPose();
    }

}
