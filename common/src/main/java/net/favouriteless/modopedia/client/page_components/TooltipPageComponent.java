package net.favouriteless.modopedia.client.page_components;

import net.favouriteless.modopedia.api.Lookup;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.book.page_components.PageComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.stream.Collectors;

public class TooltipPageComponent extends PageComponent {

    protected List<Component> tooltipLines;
    protected int width;
    protected int height;

    @Override
    public void init(Book book, Lookup lookup, Level level) {
        super.init(book, lookup, level);
        width = lookup.get("width").asInt();
        height = lookup.get("height").asInt();
        tooltipLines = lookup.get("tooltip").asStream()
                .map(var -> Component.translatable(var.asString()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick) {
        if(context.isHovered(mouseX, mouseY, x, y, width, height))
            graphics.renderComponentTooltip(Minecraft.getInstance().font, tooltipLines, mouseX, mouseY);
    }

}
