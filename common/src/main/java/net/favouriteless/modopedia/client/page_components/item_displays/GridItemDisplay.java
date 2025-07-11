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
            Codec.INT.optionalFieldOf("columns", Integer.MAX_VALUE).forGetter(d -> d.columns),
            Codec.INT.optionalFieldOf("padding", 16).forGetter(d -> d.padding),
            Codec.BOOL.optionalFieldOf("centered", false).forGetter(d -> d.centered)
    ).apply(instance, GridItemDisplay::new));

    protected final List<ItemDisplay> displays;
    protected final int columns;
    protected final int padding;
    protected final boolean centered;

    public GridItemDisplay(List<ItemDisplay> displays, int columns, int padding, boolean centered) {
        this.displays = displays;
        this.columns = columns;
        this.padding = padding;
        this.centered = centered;

        if(displays.isEmpty())
            throw new IllegalArgumentException("GridItemDisplay cannot have zero child displays.");
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, String entry) {
        int rowCount = 0;
        for(int i = 0; i < displays.size();) {
            List<ItemDisplay> row = displays.subList(i, Math.min(i + columns, displays.size()));

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
