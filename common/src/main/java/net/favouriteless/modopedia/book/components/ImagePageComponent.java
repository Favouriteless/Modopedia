package net.favouriteless.modopedia.book.components;

import net.favouriteless.modopedia.api.Variable.Lookup;
import net.favouriteless.modopedia.api.books.BookRenderContext;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class ImagePageComponent extends PageComponent {

    protected ResourceLocation image;
    protected int width;
    protected int height;

    @Override
    public void init(Lookup lookup) {
        super.init(lookup);
        image = lookup.get("image").as(ResourceLocation.class);
        width = lookup.getOrDefault("width", 100).asInt();
        height = lookup.getOrDefault("height", 100).asInt();
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTicks) {
        graphics.blit(image, x, y, 0, 0, width, height);
    }

}
