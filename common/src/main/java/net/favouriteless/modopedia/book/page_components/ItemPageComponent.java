package net.favouriteless.modopedia.book.page_components;

import net.favouriteless.modopedia.api.Lookup;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemPageComponent extends PageComponent {

    protected ItemStack[][] items;
    protected int rowMax;
    protected int padding;

    @Override
    public void init(Book book, Lookup lookup, Level level) {
        super.init(book, lookup, level);
        items = lookup.get("items").asStream().map(v -> v.as(ItemStack[].class)).filter(a -> a.length > 0).toArray(ItemStack[][]::new);
        if(items.length == 0)
            throw new IllegalArgumentException("Item gallery cannot have zero items in it.");
        rowMax = lookup.getOrDefault("row_max", Integer.MAX_VALUE).asInt();
        padding = lookup.getOrDefault("padding", 16).asInt();

    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, context, mouseX, mouseY, partialTick);
        Font font = Minecraft.getInstance().font;

        int yOff = -padding; // Start at -padding because it'll get increased on 0
        for(int i = 0; i < items.length; i++) {
            if(i % rowMax == 0)
                yOff += padding;

            int x = this.x + (i % rowMax) * padding;
            int y = this.y + yOff;

            ItemStack[] itemArray = items[i];
            ItemStack stack = itemArray[(context.getTicks() / 20) % itemArray.length];

            graphics.renderItem(stack, x, y);
            graphics.renderItemDecorations(font, stack, x, y);

            if(context.isHovered(mouseX, mouseY, x, y, 16, 16))
                graphics.renderTooltip(font, stack, mouseX, mouseY);
        }
    }

}
