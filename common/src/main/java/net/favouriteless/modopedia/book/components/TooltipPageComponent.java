package net.favouriteless.modopedia.book.components;

import net.favouriteless.modopedia.api.Variable.Lookup;
import net.favouriteless.modopedia.api.books.BookRenderContext;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TooltipPageComponent extends PageComponent {

    protected List<Component> tooltipLines = new ArrayList<>();
    protected int width;
    protected int height;


    @Override
    public void init(Lookup lookup, Level level) {
        super.init(lookup, level);
        width = lookup.get("width").asInt();
        height = lookup.get("height").asInt();

        tooltipLines = lookup.get("tooltip").asStream()
                .map(var -> Component.literal(var.asString()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTicks) {
        if(context.isHovered(mouseX, mouseY, x, y, width, height))
            graphics.renderComponentTooltip(Minecraft.getInstance().font, tooltipLines, mouseX, mouseY);
    }

}
