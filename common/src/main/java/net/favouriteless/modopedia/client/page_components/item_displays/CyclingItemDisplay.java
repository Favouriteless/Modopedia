package net.favouriteless.modopedia.client.page_components.item_displays;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.book.page_components.ItemDisplay;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class CyclingItemDisplay implements ItemDisplay {

    public static final ResourceLocation ID = Modopedia.id("cycling");

    public static final MapCodec<CyclingItemDisplay> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ItemStack.CODEC.listOf(1, Integer.MAX_VALUE).fieldOf("items").forGetter(d -> d.items)
    ).apply(instance, CyclingItemDisplay::new));

    private final List<ItemStack> items;

    public CyclingItemDisplay(List<ItemStack> items) {
        this.items = items;
        if(items.isEmpty())
            throw new IllegalArgumentException("CyclingItemDisplay cannot have zero items.");
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, String entry) {
        ItemStack item = items.get((context.getTicks() / 20) % items.size());
        context.renderItem(graphics, item, 0, 0, mouseX, mouseY, entry);
    }

    @Override
    public MapCodec<? extends ItemDisplay> typeCodec() {
        return CODEC;
    }

}
