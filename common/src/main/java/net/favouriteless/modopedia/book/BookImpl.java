package net.favouriteless.modopedia.book;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.book.text.TextChunk;
import net.favouriteless.modopedia.book.text.TextParser;
import net.favouriteless.modopedia.util.StringUtils;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class BookImpl implements Book {

    private final ResourceLocation type;
    private final String title;
    private final String subtitle;
    private final String rawLandingText;
    private final ResourceLocation texture;
    private final ResourceLocation itemModel;
    private final String defaultLang;

    private final ResourceLocation font;
    private final int textColour;
    private final int headerColour;
    private final int lineWidth;

    public BookImpl(String title, String subtitle, ResourceLocation type, String rawLandingText,
                    ResourceLocation texture, ResourceLocation itemModel, String defaultLang, ResourceLocation font,
                    int textColour, int headerColour, int lineWidth) {
        this.type = type;
        this.title = title;
        this.subtitle = subtitle;
        this.rawLandingText = rawLandingText;
        this.texture = texture;
        this.itemModel = itemModel;
        this.defaultLang = defaultLang;
        this.font = font;
        this.textColour = textColour;
        this.headerColour = headerColour;
        this.lineWidth = lineWidth;
    }

    @Override
    public ResourceLocation getType() {
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
    public String getDefaultLanguage() {
        return defaultLang;
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

    // ------------------------------------ Below this point is non-API functions ------------------------------------

    public static final Codec<Book> PERSISTENT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("title").forGetter(Book::getTitle),
            Codec.STRING.optionalFieldOf("subtitle").forGetter(c -> Optional.ofNullable(c.getSubtitle())),
            ResourceLocation.CODEC.optionalFieldOf("type", Modopedia.id("classic")).forGetter(Book::getType),
            Codec.STRING.optionalFieldOf("landing_text").forGetter(c -> Optional.ofNullable(c.getRawLandingText())),
            ResourceLocation.CODEC.optionalFieldOf("texture", Modopedia.id("textures/gui/book/default.png")).forGetter(Book::getTexture),
            ResourceLocation.CODEC.optionalFieldOf("model", Modopedia.id("item/book_default")).forGetter(Book::getItemModelLocation),
            Codec.STRING.optionalFieldOf("default_language", "en_us").forGetter(Book::getDefaultLanguage),
            ResourceLocation.CODEC.optionalFieldOf("font", Modopedia.id("default")).forGetter(Book::getFont),
            Codec.STRING.optionalFieldOf("text_colour", "000000").forGetter(book -> Integer.toHexString(book.getTextColour())),
            Codec.STRING.optionalFieldOf("header_colour", "000000").forGetter(book -> Integer.toHexString(book.getHeaderColour())),
            Codec.INT.optionalFieldOf("line_width", 100).forGetter(Book::getLineWidth)
    ).apply(instance, (title, subtitle, type, landingText, texture, model, lang, font, textColour, headerColour, lineWidth) ->
                       new BookImpl(title, subtitle.orElse(null), type, landingText.orElse(null), texture,
                               model, lang, font, Integer.parseInt(textColour, 16), Integer.parseInt(headerColour, 16),
                               lineWidth))
    );

    public static final StreamCodec<ByteBuf, Book> STREAM_CODEC = new StreamCodec<>() { // StreamCodec.composite overloads were too small :(

        @Override
        public Book decode(ByteBuf buf) {
            return new BookImpl(
                    ByteBufCodecs.STRING_UTF8.decode(buf),
                    ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8).decode(buf).orElse(null),
                    ResourceLocation.STREAM_CODEC.decode(buf),
                    ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8).decode(buf).orElse(null),
                    ResourceLocation.STREAM_CODEC.decode(buf),
                    ResourceLocation.STREAM_CODEC.decode(buf),
                    ByteBufCodecs.STRING_UTF8.decode(buf),
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
            ResourceLocation.STREAM_CODEC.encode(buf, book.getType());
            ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8).encode(buf, Optional.ofNullable(book.getRawLandingText()));
            ResourceLocation.STREAM_CODEC.encode(buf, book.getTexture());
            ResourceLocation.STREAM_CODEC.encode(buf, book.getItemModelLocation());
            ByteBufCodecs.STRING_UTF8.encode(buf, book.getDefaultLanguage());
            ResourceLocation.STREAM_CODEC.encode(buf, book.getFont());
            ByteBufCodecs.INT.encode(buf, book.getTextColour());
            ByteBufCodecs.INT.encode(buf, book.getHeaderColour());
            ByteBufCodecs.INT.encode(buf, book.getLineWidth());
        }

    };

}