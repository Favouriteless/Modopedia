package net.favouriteless.modopedia.api.books;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

/**
 * Links a book texture to its page pages-- this ensures everybody can use custom textures and not have to worry about
 * forcing the texture to line up with where Modopedia expects the pages to be!
 */
public record BookTexture(ResourceLocation location, int width, int height, int texSize, List<PageDetails> pages) {

    public static final Codec<BookTexture> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("texture").forGetter(t -> t.location),
        Codec.INT.fieldOf("width").forGetter(t -> t.width),
        Codec.INT.fieldOf("height").forGetter(t -> t.height),
        Codec.INT.fieldOf("tex_size").forGetter(t -> t.texSize),
        PageDetails.CODEC.listOf(1, Integer.MAX_VALUE).fieldOf("pages").forGetter(t -> t.pages)
    ).apply(instance, BookTexture::new));

    public static BookTexture of(ResourceLocation location, int width, int height, int texSize, PageDetails... details) {
        return of(location, width, height, texSize, List.of(details));
    }

    public static BookTexture of(ResourceLocation location, int width, int height, int texSize, List<PageDetails> details) {
        return new BookTexture(location, width, height, texSize, details);
    }

    /**
     * Represents the position and dimensions of a Page on a {@link BookTexture}.
     */
    public record PageDetails(int x, int y, int width, int height) {

        public static final Codec<PageDetails> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("x").forGetter(p -> p.x),
                Codec.INT.fieldOf("y").forGetter(p -> p.y),
                Codec.INT.fieldOf("width").forGetter(p -> p.width),
                Codec.INT.fieldOf("height").forGetter(p -> p.height)
        ).apply(instance, PageDetails::of));

        public static PageDetails of(int x, int y, int width, int height) {
            return new PageDetails(x, y, width, height);
        }

    }

}
