package net.favouriteless.modopedia.api.books;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

/**
 * Links a {@link Book} to various rendering information. (You only need to care about this if you're creating your own
 * screens)
 */
public record BookTexture(ResourceLocation location, int width, int height, int texWidth, int texHeight,
                          List<Rectangle> pages, WidgetDetails titleBacker, WidgetDetails left, WidgetDetails right,
                          WidgetDetails back, WidgetDetails refresh, Rectangle separator) {

    public static final Codec<BookTexture> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("texture").forGetter(BookTexture::location),
        Codec.INT.fieldOf("width").forGetter(BookTexture::width),
        Codec.INT.fieldOf("height").forGetter(BookTexture::height),
        Codec.INT.fieldOf("tex_width").forGetter(BookTexture::texWidth),
        Codec.INT.fieldOf("tex_height").forGetter(BookTexture::texHeight),
        Rectangle.CODEC.listOf(1, Integer.MAX_VALUE).fieldOf("pages").forGetter(BookTexture::pages),
        WidgetDetails.CODEC.fieldOf("title_backer").forGetter(BookTexture::titleBacker),
        WidgetDetails.CODEC.fieldOf("left_button").forGetter(BookTexture::left),
        WidgetDetails.CODEC.fieldOf("right_button").forGetter(BookTexture::right),
        WidgetDetails.CODEC.fieldOf("back_button").forGetter(BookTexture::back),
        WidgetDetails.CODEC.fieldOf("refresh_button").forGetter(BookTexture::refresh),
        Rectangle.CODEC.fieldOf("separator").forGetter(BookTexture::separator)
    ).apply(instance, BookTexture::new));



    public record Rectangle(int x, int y, int width, int height) {

        public static final Codec<Rectangle> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("x").forGetter(Rectangle::x),
                Codec.INT.fieldOf("y").forGetter(Rectangle::y),
                Codec.INT.fieldOf("width").forGetter(Rectangle::width),
                Codec.INT.fieldOf("height").forGetter(Rectangle::height)
        ).apply(instance, Rectangle::new));

    }

    public record WidgetDetails(int x, int y, int width, int height, int u, int v) {

        public static final Codec<WidgetDetails> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("x").forGetter(WidgetDetails::x),
                Codec.INT.fieldOf("y").forGetter(WidgetDetails::y),
                Codec.INT.fieldOf("width").forGetter(WidgetDetails::width),
                Codec.INT.fieldOf("height").forGetter(WidgetDetails::height),
                Codec.INT.fieldOf("u").forGetter(WidgetDetails::u),
                Codec.INT.fieldOf("v").forGetter(WidgetDetails::v)
        ).apply(instance, WidgetDetails::new));

    }

}
