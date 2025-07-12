package net.favouriteless.modopedia.client.page_components.item_displays;

import com.mojang.serialization.MapCodec;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.book.page_components.ItemDisplay;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class EmptyItemDisplay implements ItemDisplay {

    public static final ResourceLocation ID = Modopedia.id("empty");

    public static final MapCodec<EmptyItemDisplay> CODEC = MapCodec.unit(new EmptyItemDisplay());

    public EmptyItemDisplay() {}

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, String entry) {}

    @Override
    public MapCodec<? extends ItemDisplay> typeCodec() {
        return CODEC;
    }

}
