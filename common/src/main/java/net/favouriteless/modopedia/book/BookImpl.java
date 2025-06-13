package net.favouriteless.modopedia.book;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookType;
import net.favouriteless.modopedia.common.book_types.ClassicBookType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BookImpl implements Book {

    private final BookType type;
    private final String title;
    private final String subtitle;
    private final String rawLandingText;
    private final ResourceLocation texture;
    private final ResourceLocation itemModel;
    private final ResourceKey<CreativeModeTab> tab;

    private final ResourceLocation font;
    private final int textColour;
    private final int headerColour;
    private final int lineWidth;

    public BookImpl(String title, String subtitle, BookType type, String rawLandingText, ResourceLocation texture,
                    ResourceLocation itemModel, ResourceKey<CreativeModeTab> tab, ResourceLocation font, int textColour,
                    int headerColour, int lineWidth) {
        this.type = type;
        this.title = title;
        this.subtitle = subtitle;
        this.rawLandingText = rawLandingText;
        this.texture = texture;
        this.itemModel = itemModel;
        this.tab = tab;
        this.font = font;
        this.textColour = textColour;
        this.headerColour = headerColour;
        this.lineWidth = lineWidth;
    }

    @Override
    public BookType getType() {
        return type;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public String getSubtitle() {
        return subtitle;
    }

    @Nullable
    @Override
    public String getRawLandingText() {
        return rawLandingText;
    }

    @Override
    public ResourceLocation getTexture() {
        return texture;
    }

    @Override
    public ResourceLocation getItemModelLocation() {
        return itemModel;
    }

    @Override
    public ResourceLocation getFont() {
        return font;
    }

    @Override
    public int getTextColour() {
        return textColour;
    }

    @Override
    public int getHeaderColour() {
        return headerColour;
    }

    @Override
    public int getLineWidth() {
        return lineWidth;
    }

    @Override
    public ResourceKey<CreativeModeTab> getCreativeTab() {
        return tab;
    }

    // ------------------------------------ Below this point is non-API functions ------------------------------------

    public static final Codec<Book> PERSISTENT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("title").forGetter(Book::getTitle),
            Codec.STRING.optionalFieldOf("subtitle").forGetter(b -> Optional.ofNullable(b.getSubtitle())),
            BookType.codec().optionalFieldOf("type", new ClassicBookType()).forGetter(Book::getType),
            Codec.STRING.optionalFieldOf("landing_text").forGetter(b -> Optional.ofNullable(b.getRawLandingText())),
            ResourceLocation.CODEC.optionalFieldOf("texture", Modopedia.id("default")).forGetter(Book::getTexture),
            ResourceLocation.CODEC.optionalFieldOf("model", Modopedia.id("item/default")).forGetter(Book::getItemModelLocation),
            ResourceKey.codec(Registries.CREATIVE_MODE_TAB).optionalFieldOf("creative_tab").forGetter(b -> Optional.ofNullable(b.getCreativeTab())),
            ResourceLocation.CODEC.optionalFieldOf("font", Modopedia.id("default")).forGetter(Book::getFont),
            Codec.STRING.optionalFieldOf("text_colour", "000000").forGetter(b -> Integer.toHexString(b.getTextColour())),
            Codec.STRING.optionalFieldOf("header_colour", "000000").forGetter(b -> Integer.toHexString(b.getHeaderColour())),
            Codec.INT.optionalFieldOf("line_width", 100).forGetter(Book::getLineWidth)
    ).apply(instance, (title, subtitle, type, landingText, texture, model, tab, font, textColour, headerColour, lineWidth) ->
            new BookImpl(title, subtitle.orElse(null), type, landingText.orElse(null), texture,
                    model, tab.orElse(null), font, Integer.parseInt(textColour, 16), Integer.parseInt(headerColour, 16),
                    lineWidth))
    );

    public static final StreamCodec<ByteBuf, Book> STREAM_CODEC = new StreamCodec<>() { // StreamCodec.composite overloads were too small :(

        @Override
        @SuppressWarnings("rawtypes")
        public Book decode(ByteBuf buf) {
            return new BookImpl(
                    ByteBufCodecs.STRING_UTF8.decode(buf),
                    ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8).decode(buf).orElse(null),
                    ByteBufCodecs.fromCodec(BookType.codec()).decode(buf),
                    ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8).decode(buf).orElse(null),
                    ResourceLocation.STREAM_CODEC.decode(buf),
                    ResourceLocation.STREAM_CODEC.decode(buf),
                    ByteBufCodecs.optional(ResourceKey.streamCodec(Registries.CREATIVE_MODE_TAB)).decode(buf).orElse(null),
                    ResourceLocation.STREAM_CODEC.decode(buf),
                    ByteBufCodecs.INT.decode(buf),
                    ByteBufCodecs.INT.decode(buf),
                    ByteBufCodecs.INT.decode(buf)
            );
        }

        @Override
        public void encode(ByteBuf buf, Book book) {
            ByteBufCodecs.STRING_UTF8.encode(buf, book.getTitle());
            ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8).encode(buf, Optional.ofNullable(book.getSubtitle()));
            ByteBufCodecs.fromCodec(BookType.codec()).encode(buf, book.getType());
            ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8).encode(buf, Optional.ofNullable(book.getRawLandingText()));
            ResourceLocation.STREAM_CODEC.encode(buf, book.getTexture());
            ResourceLocation.STREAM_CODEC.encode(buf, book.getItemModelLocation());
            ByteBufCodecs.optional(ResourceKey.streamCodec(Registries.CREATIVE_MODE_TAB)).encode(buf, Optional.ofNullable(book.getCreativeTab()));
            ResourceLocation.STREAM_CODEC.encode(buf, book.getFont());
            ByteBufCodecs.INT.encode(buf, book.getTextColour());
            ByteBufCodecs.INT.encode(buf, book.getHeaderColour());
            ByteBufCodecs.INT.encode(buf, book.getLineWidth());
        }
    };

}