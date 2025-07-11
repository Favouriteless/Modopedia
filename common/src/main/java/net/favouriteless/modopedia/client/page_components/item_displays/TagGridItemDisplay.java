package net.favouriteless.modopedia.client.page_components.item_displays;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.page_components.ItemDisplay;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class TagGridItemDisplay extends GridItemDisplay {

    public static final ResourceLocation ID = Modopedia.id("tag_grid");

    public static final MapCodec<TagGridItemDisplay> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            TagKey.codec(Registries.ITEM).fieldOf("tag").forGetter(d -> d.tag),
            Codec.INT.optionalFieldOf("columns", Integer.MAX_VALUE).forGetter(d -> d.columns),
            Codec.INT.optionalFieldOf("padding", 16).forGetter(d -> d.padding),
            Codec.BOOL.optionalFieldOf("centered", false).forGetter(d -> d.centered)
    ).apply(instance, TagGridItemDisplay::new));

    private final TagKey<Item> tag;

    public TagGridItemDisplay(TagKey<Item> tag, int columns, int padding, boolean centered) {
        super(BuiltInRegistries.ITEM.getTag(tag)
                .orElseThrow(() -> new IllegalArgumentException(tag + " is not a valid item tag")).stream()
                        .map(i -> (ItemDisplay)new SimpleItemDisplay(i.value().getDefaultInstance())).toList(),
                columns, padding, centered);
        this.tag = tag;
    }

    @Override
    public MapCodec<? extends ItemDisplay> typeCodec() {
        return CODEC;
    }

}
