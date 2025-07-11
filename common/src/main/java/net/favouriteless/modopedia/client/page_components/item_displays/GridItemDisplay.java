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

import java.util.List;

public class GridItemDisplay implements ItemDisplay {

    public static final ResourceLocation ID = Modopedia.id("grid");

    public static final MapCodec<GridItemDisplay> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.lazyInitialized(ItemDisplay::codec).listOf().fieldOf("displays").forGetter(d -> d.displays),
            Codec.INT.optionalFieldOf("row_max", Integer.MAX_VALUE).forGetter(d -> d.rowMax),
            Codec.INT.optionalFieldOf("padding", 17).forGetter(d -> d.padding),
            Codec.BOOL.optionalFieldOf("centered", false).forGetter(d -> d.centered)
    ).apply(instance, GridItemDisplay::new));

    private final List<ItemDisplay> displays;
    private final int rowMax;
    private final int padding;
    private final boolean centered;

    public GridItemDisplay(List<ItemDisplay> displays, int rowMax, int padding, boolean centered) {
        this.displays = displays;
        this.rowMax = rowMax;
        this.padding = padding;
        this.centered = centered;
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, String entry) {
        int rowCount = 0;
        for(int i = 0; i < displays.size();) {
            List<ItemDisplay> row = displays.subList(i, Math.min(i + rowMax, displays.size()));

            PoseStack pose = graphics.pose();
            int x = centered ? -(row.size() * padding / 2) : 0;
            int y = rowCount * padding;

            for(ItemDisplay display : row) {
                pose.pushPose();
                pose.translate(x, y, 0);
                display.render(graphics, context, mouseX - x, mouseY - y, entry);
                pose.popPose();
                x += padding;
            }

            rowCount++;
            i += row.size();
        }
    }

    @Override
    public MapCodec<? extends ItemDisplay> typeCodec() {
        return CODEC;
    }

}
