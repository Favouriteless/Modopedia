package net.favouriteless.modopedia.client.page_components.item_displays;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.book.page_components.ItemDisplay;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class TagItemDisplay implements ItemDisplay {

    public static final ResourceLocation ID = Modopedia.id("tag");

    public static final MapCodec<TagItemDisplay> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            TagKey.codec(Registries.ITEM).fieldOf("tag").forGetter(d -> d.tag)
    ).apply(instance, TagItemDisplay::new));

    private final TagKey<Item> tag;
    private final Named<Item> items;

    public TagItemDisplay(TagKey<Item> tag) {
        Optional<Named<Item>> optional = BuiltInRegistries.ITEM.getTag(tag);
        if(optional.isEmpty())
            throw new IllegalArgumentException(tag + " is not a valid item tag");

        this.tag = tag;
        this.items = optional.get();
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, String entry) {
        ItemStack item = items.get((context.getTicks() / 20) % items.size()).value().getDefaultInstance();
        context.renderItem(graphics, item, 0, 0, mouseX, mouseY, entry);
    }

    @Override
    public MapCodec<? extends ItemDisplay> typeCodec() {
        return CODEC;
    }

}
