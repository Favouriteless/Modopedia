package net.favouriteless.modopedia.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.api.books.BookTexture;
import net.favouriteless.modopedia.api.books.BookTexture.FixedRectangle;
import net.favouriteless.modopedia.api.books.BookTexture.Rectangle;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;

public class MExtraCodecs {

    public static Codec<List<ItemStack>> ITEM_LIST = Codec.withAlternative(
            TagKey.codec(Registries.ITEM).flatXmap(
                    tag -> {
                        Optional<Named<Item>> optional = BuiltInRegistries.ITEM.getTag(tag);
                        return optional.map(holders -> DataResult.success(holders.stream().map(h -> h.value().getDefaultInstance()).toList()))
                                .orElseGet(() -> DataResult.error(() -> tag + " is not a valid item tag"));
                    },
                    list -> {
                        TagKey<Item> key = TagUtils.findItemTag(list);
                        return key != null ? DataResult.success(key) : DataResult.error(() -> "");
                    }
            ),
            ItemStack.CODEC.listOf()
    );

    public static final Codec<Character> CHAR = Codec.STRING.comapFlatMap(
            s -> s.length() == 1 ? DataResult.success(s.charAt(0)) : DataResult.error(() -> (s + " is not a valid character")),
            String::valueOf
    );

    public static final Codec<Rectangle> RECTANGLE = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("u").forGetter(Rectangle::u),
            Codec.INT.fieldOf("v").forGetter(Rectangle::v),
            Codec.INT.fieldOf("width").forGetter(Rectangle::width),
            Codec.INT.fieldOf("height").forGetter(Rectangle::height)
    ).apply(instance, Rectangle::new));

    public static final Codec<FixedRectangle> FIXED_RECTANGLE = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("x").forGetter(FixedRectangle::x),
            Codec.INT.fieldOf("y").forGetter(FixedRectangle::y),
            Codec.INT.fieldOf("u").forGetter(FixedRectangle::u),
            Codec.INT.fieldOf("v").forGetter(FixedRectangle::v),
            Codec.INT.fieldOf("width").forGetter(FixedRectangle::width),
            Codec.INT.fieldOf("height").forGetter(FixedRectangle::height)
    ).apply(instance, FixedRectangle::new));

    public static final Codec<BookTexture> BOOK_TEXTURE = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(BookTexture::location),
            Codec.INT.fieldOf("width").forGetter(BookTexture::width),
            Codec.INT.fieldOf("height").forGetter(BookTexture::height),
            Codec.INT.fieldOf("tex_width").forGetter(BookTexture::texWidth),
            Codec.INT.fieldOf("tex_height").forGetter(BookTexture::texHeight),
            RECTANGLE.listOf(1, Integer.MAX_VALUE).fieldOf("pages").forGetter(BookTexture::pages),
            FIXED_RECTANGLE.fieldOf("title_backer").forGetter(BookTexture::titleBacker),
            FIXED_RECTANGLE.fieldOf("left_button").forGetter(BookTexture::left),
            FIXED_RECTANGLE.fieldOf("right_button").forGetter(BookTexture::right),
            FIXED_RECTANGLE.fieldOf("back_button").forGetter(BookTexture::back),
            FIXED_RECTANGLE.fieldOf("refresh_button").forGetter(BookTexture::refresh),
            RECTANGLE.optionalFieldOf("separator", Rectangle.ZERO).forGetter(BookTexture::separator),
            RECTANGLE.optionalFieldOf("item_frame", Rectangle.ZERO).forGetter(BookTexture::itemFrame),
            RECTANGLE.optionalFieldOf("small_frame", Rectangle.ZERO).forGetter(BookTexture::smallFrame),
            RECTANGLE.optionalFieldOf("large_frame", Rectangle.ZERO).forGetter(BookTexture::largeFrame),
            RECTANGLE.optionalFieldOf("crafting_frame", Rectangle.ZERO).forGetter(BookTexture::craftingFrame)
    ).apply(instance, BookTexture::new));

}
