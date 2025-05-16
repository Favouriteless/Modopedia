package net.favouriteless.modopedia.api.books;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

/**
 * Links a book texture to its page pages-- this ensures everybody can use custom textures and not have to worry about
 * forcing the texture to line up with where Modopedia expects the pages to be!
 */
public record BookTexture(ResourceLocation location, int width, int height, int texWidth, int texHeight, List<Rectangle> pages,
                          WidgetDetails left, WidgetDetails right, WidgetDetails back, WidgetDetails refresh, Rectangle separator) {

    public static final Codec<BookTexture> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("texture").forGetter(t -> t.location),
        Codec.INT.fieldOf("width").forGetter(t -> t.width),
        Codec.INT.fieldOf("height").forGetter(t -> t.height),
        Codec.INT.fieldOf("tex_width").forGetter(t -> t.texWidth),
        Codec.INT.fieldOf("tex_height").forGetter(t -> t.texHeight),
        Rectangle.CODEC.listOf(1, Integer.MAX_VALUE).fieldOf("pages").forGetter(t -> t.pages),
        WidgetDetails.CODEC.fieldOf("left_button").forGetter(t -> t.left),
        WidgetDetails.CODEC.fieldOf("right_button").forGetter(t -> t.right),
        WidgetDetails.CODEC.fieldOf("back_button").forGetter(t -> t.back),
        WidgetDetails.CODEC.fieldOf("refresh_button").forGetter(t -> t.refresh),
        Rectangle.CODEC.fieldOf("separator").forGetter(t -> t.separator)
    ).apply(instance, BookTexture::new));

    /**
     * Represents the position and dimensions of a Rectangle.
     */
    public record Rectangle(int x, int y, int width, int height) {

        public static final Codec<Rectangle> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("x").forGetter(p -> p.x),
                Codec.INT.fieldOf("y").forGetter(p -> p.y),
                Codec.INT.fieldOf("width").forGetter(p -> p.width),
                Codec.INT.fieldOf("height").forGetter(p -> p.height)
        ).apply(instance, Rectangle::new));

        public boolean contains(double x, double y) {
            return x > this.x && x < this.x+width && y > this.y && y < this.y+height;
        }

    }

    public record WidgetDetails(int x, int y, int width, int height, int u, int v) {

        public static final Codec<WidgetDetails> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("x").forGetter(w -> w.x),
                Codec.INT.fieldOf("y").forGetter(w -> w.y),
                Codec.INT.fieldOf("width").forGetter(w -> w.width),
                Codec.INT.fieldOf("height").forGetter(w -> w.height),
                Codec.INT.fieldOf("u").forGetter(w -> w.u),
                Codec.INT.fieldOf("v").forGetter(w -> w.v)
        ).apply(instance, WidgetDetails::new));

        public boolean contains(double x, double y) {
            return x > this.x && x < this.x+width && y > this.y && y < this.y+height;
        }

    }

}
