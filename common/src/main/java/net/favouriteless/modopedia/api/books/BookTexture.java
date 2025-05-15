package net.favouriteless.modopedia.api.books;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

/**
 * Links a book texture to its page pages-- this ensures everybody can use custom textures and not have to worry about
 * forcing the texture to line up with where Modopedia expects the pages to be!
 */
public record BookTexture(ResourceLocation location, int width, int height, int texSize, List<Dimensions> pages,
                          Dimensions left, Dimensions right, Dimensions back, Dimensions refresh) {

    public static final Codec<BookTexture> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("texture").forGetter(t -> t.location),
        Codec.INT.fieldOf("width").forGetter(t -> t.width),
        Codec.INT.fieldOf("height").forGetter(t -> t.height),
        Codec.INT.fieldOf("tex_size").forGetter(t -> t.texSize),
        Dimensions.CODEC.listOf(1, Integer.MAX_VALUE).fieldOf("pages").forGetter(t -> t.pages),
        Dimensions.CODEC.optionalFieldOf("left_button").forGetter(t -> Optional.ofNullable(t.left)),
        Dimensions.CODEC.optionalFieldOf("right_button").forGetter(t -> Optional.ofNullable(t.right)),
        Dimensions.CODEC.optionalFieldOf("back_button").forGetter(t -> Optional.ofNullable(t.back)),
        Dimensions.CODEC.optionalFieldOf("refresh_button").forGetter(t -> Optional.ofNullable(t.refresh))
    ).apply(instance, (tex, width, height, texSize, pages, left, right, back, refresh) -> new BookTexture(tex, width,
            height, texSize, pages, left.orElse(null), right.orElse(null), back.orElse(null),
            refresh.orElse(null)))
    );

    /**
     * Represents the position and dimensions of a Page or Widget on a {@link BookTexture}.
     */
    public record Dimensions(int x, int y, int width, int height) {

        public static final Codec<Dimensions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("x").forGetter(p -> p.x),
                Codec.INT.fieldOf("y").forGetter(p -> p.y),
                Codec.INT.fieldOf("width").forGetter(p -> p.width),
                Codec.INT.fieldOf("height").forGetter(p -> p.height)
        ).apply(instance, Dimensions::new));

        public boolean contains(double x, double y) {
            return x > this.x && x < this.x+width && y > this.y && y < this.y+height;
        }

    }
}
