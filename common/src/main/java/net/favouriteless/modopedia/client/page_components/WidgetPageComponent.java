package net.favouriteless.modopedia.client.page_components;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.BookTexture;
import net.favouriteless.modopedia.api.book.BookTexture.Rectangle;
import net.favouriteless.modopedia.api.book.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.book.page_components.PageComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class WidgetPageComponent extends PageComponent {

    public static final ResourceLocation ID_SMALL_FRAME = Modopedia.id("small_frame");
    public static final ResourceLocation ID_MEDIUM_FRAME = Modopedia.id("medium_frame");
    public static final ResourceLocation ID_LARGE_FRAME = Modopedia.id("large_frame");
    public static final ResourceLocation ID_CRAFTING_GRID = Modopedia.id("crafting_grid");
    public static final ResourceLocation ID_CRAFTING_ARROW = Modopedia.id("crafting_arrow");
    public static final ResourceLocation ID_CRAFTING_FLAME = Modopedia.id("crafting_flame");

    protected final String id;

    public WidgetPageComponent(String id) {
        this.id = id;
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick) {
        BookTexture tex = context.getBookTexture();
        Rectangle rec = tex.widgets().get(id);

        if(rec == null)
            return;

        graphics.blit(tex.location(), x, y, rec.width(), rec.height(), rec.u(), rec.v(), rec.width(), rec.height(), tex.texWidth(), tex.texHeight());
    }

}
