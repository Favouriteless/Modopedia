package net.favouriteless.modopedia.book.page_components;

import net.favouriteless.modopedia.api.Variable.Lookup;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.api.books.page_components.PageWidgetHolder;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class ImagePageComponent extends PageComponent {

    protected ResourceLocation[] images;
    protected int width;
    protected int height;

    protected int selectedImage = 0;

    @Override
    public void init(Book book, Lookup lookup, Level level) {
        super.init(book, lookup, level);
        images = lookup.get("images").as(ResourceLocation[].class);
        width = lookup.getOrDefault("width", 100).asInt();
        height = lookup.getOrDefault("height", 100).asInt();
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick) {
        // Sizes look weird-- it's actually to force the texture to stretch to the size of the widget.
        graphics.blit(images[0], x, y, 0, 0, 0, width, height, width, height);
    }

    @Override
    public void initWidgets(PageWidgetHolder holder) {
        super.initWidgets(holder);
    }

}
