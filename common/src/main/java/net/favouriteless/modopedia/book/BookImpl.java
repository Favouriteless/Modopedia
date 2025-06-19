package net.favouriteless.modopedia.book;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookType;
import net.favouriteless.modopedia.common.book_types.ClassicBookType;
import net.favouriteless.modopedia.common.book_types.LockedViewType;
import net.favouriteless.modopedia.common.init.MSoundEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BookImpl implements Book {

    public static final BookType DEFAULT_TYPE = new ClassicBookType(LockedViewType.HIDDEN);
    public static final ResourceLocation DEFAULT_TEXTURE = Modopedia.id("brown_brass");
    public static final ResourceLocation DEFAULT_ITEM_MODEL = Modopedia.id("item/modopedia_books/brown_brass");
    public static final Holder<SoundEvent> DEFAULT_OPEN_SOUND = MSoundEvents.BOOK_OPEN;
    public static final Holder<SoundEvent> DEFAULT_FLIP_SOUND = MSoundEvents.BOOK_FLIP;
    public static final ResourceLocation DEFAULT_FONT = Modopedia.id("default");
    public static final int DEFAULT_TEXT_COLOUR = 0x000000;
    public static final int DEFAULT_HEADER_COLOUR = 0x000000;
    public static final int DEFAULT_LINE_WIDTH = 100;

    private final BookType type;
    private final String title;
    private final String subtitle;
    private final String rawLandingText;
    private final ResourceLocation texture;
    private final ResourceLocation itemModel;
    private final ResourceKey<CreativeModeTab> tab;
    private final Holder<SoundEvent> openSound;
    private final Holder<SoundEvent> flipSound;

    private final ResourceLocation font;
    private final int textColour;
    private final int headerColour;
    private final int lineWidth;

    public BookImpl(String title, String subtitle, BookType type, String rawLandingText, ResourceLocation texture,
                    ResourceLocation itemModel, ResourceKey<CreativeModeTab> tab, Holder<SoundEvent> openSound,
                    Holder<SoundEvent> flipSound, ResourceLocation font, int textColour, int headerColour, int lineWidth) {
        this.type = type;
        this.title = title;
        this.subtitle = subtitle;
        this.rawLandingText = rawLandingText;
        this.texture = texture;
        this.itemModel = itemModel;
        this.tab = tab;
        this.openSound = openSound;
        this.flipSound = flipSound;
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
    public Holder<SoundEvent> getOpenSound() {
        return openSound;
    }

    @Override
    public Holder<SoundEvent> getFlipSound() {
        return flipSound;
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
            BookType.codec().optionalFieldOf("type", DEFAULT_TYPE).forGetter(Book::getType),
            Codec.STRING.optionalFieldOf("landing_text").forGetter(b -> Optional.ofNullable(b.getRawLandingText())),
            ResourceLocation.CODEC.optionalFieldOf("texture", DEFAULT_TEXTURE).forGetter(Book::getTexture),
            ResourceLocation.CODEC.optionalFieldOf("model", DEFAULT_ITEM_MODEL).forGetter(Book::getItemModelLocation),
            ResourceKey.codec(Registries.CREATIVE_MODE_TAB).optionalFieldOf("creative_tab").forGetter(b -> Optional.ofNullable(b.getCreativeTab())),
            SoundEvent.CODEC.optionalFieldOf("open_sound", DEFAULT_OPEN_SOUND).forGetter(Book::getOpenSound),
            SoundEvent.CODEC.optionalFieldOf("flip_sound",DEFAULT_FLIP_SOUND).forGetter(Book::getFlipSound),
            ResourceLocation.CODEC.optionalFieldOf("font", DEFAULT_FONT).forGetter(Book::getFont),
            Codec.STRING.optionalFieldOf("text_colour", Integer.toHexString(DEFAULT_TEXT_COLOUR)).forGetter(b -> Integer.toHexString(b.getTextColour())),
            Codec.STRING.optionalFieldOf("header_colour", Integer.toHexString(DEFAULT_HEADER_COLOUR)).forGetter(b -> Integer.toHexString(b.getHeaderColour())),
            Codec.INT.optionalFieldOf("line_width", DEFAULT_LINE_WIDTH).forGetter(Book::getLineWidth)
    ).apply(instance, (title, subtitle, type, landingText, texture, model, tab, openSound, flipSound, font, textColour, headerColour, lineWidth) ->
            new BookImpl(title, subtitle.orElse(null), type, landingText.orElse(null), texture,
                    model, tab.orElse(null), openSound, flipSound, font, Integer.parseInt(textColour, 16),
                    Integer.parseInt(headerColour, 16), lineWidth))
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, Book> STREAM_CODEC = new StreamCodec<>() { // StreamCodec.composite overloads were too small :(

        @Override
        public Book decode(RegistryFriendlyByteBuf buf) {
            return new BookImpl(
                    ByteBufCodecs.STRING_UTF8.decode(buf),
                    ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8).decode(buf).orElse(null),
                    ByteBufCodecs.fromCodec(BookType.codec()).decode(buf),
                    ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8).decode(buf).orElse(null),
                    ResourceLocation.STREAM_CODEC.decode(buf),
                    ResourceLocation.STREAM_CODEC.decode(buf),
                    ByteBufCodecs.optional(ResourceKey.streamCodec(Registries.CREATIVE_MODE_TAB)).decode(buf).orElse(null),
                    SoundEvent.STREAM_CODEC.decode(buf),
                    SoundEvent.STREAM_CODEC.decode(buf),
                    ResourceLocation.STREAM_CODEC.decode(buf),
                    ByteBufCodecs.INT.decode(buf),
                    ByteBufCodecs.INT.decode(buf),
                    ByteBufCodecs.INT.decode(buf)
            );
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, Book book) {
            ByteBufCodecs.STRING_UTF8.encode(buf, book.getTitle());
            ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8).encode(buf, Optional.ofNullable(book.getSubtitle()));
            ByteBufCodecs.fromCodec(BookType.codec()).encode(buf, book.getType());
            ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8).encode(buf, Optional.ofNullable(book.getRawLandingText()));
            ResourceLocation.STREAM_CODEC.encode(buf, book.getTexture());
            ResourceLocation.STREAM_CODEC.encode(buf, book.getItemModelLocation());
            ByteBufCodecs.optional(ResourceKey.streamCodec(Registries.CREATIVE_MODE_TAB)).encode(buf, Optional.ofNullable(book.getCreativeTab()));
            SoundEvent.STREAM_CODEC.encode(buf, book.getOpenSound());
            SoundEvent.STREAM_CODEC.encode(buf, book.getFlipSound());
            ResourceLocation.STREAM_CODEC.encode(buf, book.getFont());
            ByteBufCodecs.INT.encode(buf, book.getTextColour());
            ByteBufCodecs.INT.encode(buf, book.getHeaderColour());
            ByteBufCodecs.INT.encode(buf, book.getLineWidth());
        }
    };

}