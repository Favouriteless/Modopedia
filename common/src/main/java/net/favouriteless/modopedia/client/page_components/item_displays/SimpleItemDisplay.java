package net.favouriteless.modopedia.client.page_components.item_displays;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.book.page_components.ItemDisplay;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class SimpleItemDisplay implements ItemDisplay {

    public static final ResourceLocation ID = Modopedia.id("simple");

    public static final MapCodec<SimpleItemDisplay> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ItemStack.CODEC.fieldOf("item").forGetter(d -> d.item)
    ).apply(instance, SimpleItemDisplay::new));

    private final ItemStack item;

    public SimpleItemDisplay(ItemStack item) {
        this.item = item;
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, String entry) {
        context.renderItem(graphics, item, 0, 0, mouseX, mouseY, entry);
    }

    @Override
    public MapCodec<? extends ItemDisplay> typeCodec() {
        return CODEC;
    }

}
