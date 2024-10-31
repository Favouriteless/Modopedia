package net.favouriteless.modopedia.book.components;

import net.favouriteless.modopedia.api.Variable.Lookup;
import net.favouriteless.modopedia.api.books.BookRenderContext;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class ImagePageComponent extends PageComponent {

    protected ResourceLocation[] images;
    protected int width;
    protected int height;

    protected int selectedImage = 0;

    @Override
    public void init(Lookup lookup, Level level) {
        super.init(lookup, level);
        images = lookup.get("images").asStream().map(var -> var.as(ResourceLocation.class)).toArray(ResourceLocation[]::new);
        width = lookup.getOrDefault("width", 100).asInt();
        height = lookup.getOrDefault("height", 100).asInt();
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTicks) {
//        graphics.blit(image, x, y, 0, 0, width, height);
    }

}
