package net.favouriteless.modopedia.client.page_components.item_displays;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.book.page_components.ItemDisplay;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.List;

public class RingsItemDisplay implements ItemDisplay {

    public static final ResourceLocation ID = Modopedia.id("rings");

    public static final MapCodec<RingsItemDisplay> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.lazyInitialized(ItemDisplay::codec).listOf().fieldOf("displays").forGetter(d -> d.displays),
            Codec.INT.optionalFieldOf("ring_max", 6).forGetter(d -> d.ringMax),
            Codec.INT.optionalFieldOf("radius", 16).forGetter(d -> d.radius),
            Codec.INT.optionalFieldOf("offset", 8).forGetter(d -> d.offset)
    ).apply(instance, RingsItemDisplay::new));


    private final List<ItemDisplay> displays;
    private final int ringMax;
    private final int radius;
    private final int offset;

    public RingsItemDisplay(List<ItemDisplay> displays, int ringMax, int radius, int offset) {
        this.displays = displays;
        this.ringMax = ringMax;
        this.radius = radius;
        this.offset = offset;
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, String entry) {
        int ringCount = 1;
        for(int i = 0; i < displays.size();) {
            List<ItemDisplay> ring = displays.subList(i, Math.min(i + ringMax * ringCount, displays.size()));

            PoseStack pose = graphics.pose();
            float a = 360.0F / ring.size() * Mth.DEG_TO_RAD;
            float sin = Mth.sin(a);
            float cos = Mth.cos(a);

            float x = 0;
            float y = -radius * ringCount;

            for(ItemDisplay display : ring) {
                int xr = Math.round(x) - offset;
                int yr = Math.round(y) - offset;

                pose.pushPose();
                pose.translate(xr, yr, 0); // Offset by half an item width to center the items.
                display.render(graphics, context, mouseX - xr, mouseY - yr, entry);
                pose.popPose();

                float xc = x; // Rotate around (0, 0)
                x = xc * cos - y * sin;
                y = xc * sin + y * cos;
            }

            ringCount++;
            i += ring.size();
        }
    }

    @Override
    public MapCodec<? extends ItemDisplay> typeCodec() {
        return CODEC;
    }

}
