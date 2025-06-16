package net.favouriteless.modopedia.client.page_components;

import com.mojang.blaze3d.platform.InputConstants;
import net.favouriteless.modopedia.api.Lookup;
import net.favouriteless.modopedia.api.ModopediaApi.EntryAssociation;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.book.page_components.PageComponent;
import net.favouriteless.modopedia.book.registries.client.ItemAssociationRegistry;
import net.favouriteless.modopedia.client.BookOpenHandler;
import net.favouriteless.modopedia.client.init.MKeyMappings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemPageComponent extends PageComponent {

    protected ItemStack[][] items;
    protected int rowMax;
    protected int padding;
    protected boolean centered;
    protected boolean reverseY;

    private boolean isHovered = false;

    @Override
    public void init(Book book, Lookup lookup, Level level) {
        super.init(book, lookup, level);
        items = lookup.get("items").asStream().map(v -> v.as(ItemStack[].class)).toArray(ItemStack[][]::new);
        if(items.length == 0)
            throw new IllegalArgumentException("Item gallery cannot have zero items in it.");
        rowMax = lookup.getOrDefault("row_max", Integer.MAX_VALUE).asInt();
        padding = lookup.getOrDefault("padding", 16).asInt();
        centered = lookup.getOrDefault("centered", false).asBoolean();
        reverseY = lookup.getOrDefault("reverse_y", false).asBoolean();
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, context, mouseX, mouseY, partialTick);
        Font font = Minecraft.getInstance().font;

        int yOff = -padding; // Start at -padding because it'll get increased on 0
        for(int i = 0; i < items.length; i++) {
            if(i % rowMax == 0)
                yOff += padding;

            ItemStack[] itemArray = items[i];
            if(itemArray.length == 0)
                continue;

            int x = this.x + (i % rowMax) * padding;
            int y = this.y + (reverseY ? -yOff : yOff);

            int row = (i-1 - (i % rowMax)) / rowMax;
            int rowWidth = (Math.min(rowMax, items.length - row * rowMax)-1) * padding + 16;
            if(centered)
                x -= rowWidth/2;

            ItemStack stack = itemArray[(context.getTicks() / 20) % itemArray.length];

            graphics.renderItem(stack, x, y);
            graphics.renderItemDecorations(font, stack, x, y);

            if(context.isHovered(mouseX, mouseY, x, y, 16, 16)) {
                graphics.renderTooltip(font, stack, mouseX, mouseY);

                String langCode = Minecraft.getInstance().options.languageCode;
                EntryAssociation association = ItemAssociationRegistry.getAssociation(langCode, BuiltInRegistries.ITEM.getKey(stack.getItem()));
                if(association != null && InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), MKeyMappings.KEY_STUDY.key.getValue())) {
                    if(association.entryId().equals(entryId))
                        return;
                    BookOpenHandler.tryOpenEntry(association.book(), association.entryId());
                }
            }
        }
    }
}
