package net.favouriteless.modopedia.book;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.Category;
import net.favouriteless.modopedia.api.books.Entry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BookImpl implements Book {

    private final ResourceLocation id;
    private final ResourceLocation type;
    private final String title;
    private final String subtitle;
    private final String rawLandingText;
    private final Component landingText;
    private final ResourceLocation texture;
    private final ResourceLocation itemModel;
    private final String defaultLang;

    private final Map<String, Map<String, Category>> categories = new HashMap<>(); // Outer map is for localisation.
    private final Map<String, Map<String, Entry>> entries = new HashMap<>();

    public BookImpl(ResourceLocation id, ResourceLocation type, String title, String subtitle, String rawLandingText,
                    ResourceLocation texture, ResourceLocation itemModel, String defaultLang) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.subtitle = subtitle;
        this.rawLandingText = rawLandingText;
        this.landingText = Component.literal(rawLandingText);
        this.texture = texture;
        this.itemModel = itemModel;
        this.defaultLang = defaultLang;
    }

    @Override
    public ResourceLocation getId() {
        return id;
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
    public Component getLandingText() {
        return landingText;
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
    public String getDefaultLanguageCode() {
        return defaultLang;
    }

    @Nullable
    @Override
    public Category getCategory(String languageCode, String id) {
        return categories.containsKey(languageCode) && categories.get(languageCode).containsKey(id) ?
                categories.get(languageCode).get(id) : categories.get(defaultLang).get(id);
    }

    @Nullable
    @Override
    public Entry getEntry(String languageCode, String id) {
        return entries.containsKey(languageCode) && entries.get(languageCode).containsKey(id) ?
                entries.get(languageCode).get(id) : entries.get(defaultLang).get(id);
    }

    // ------------------------------------ Below this point is non-API functions ------------------------------------
    
    public BookImpl addCategory(String languageCode, String id, Category category) {
        categories.get(languageCode).put(id, category);
        return this;
    }

    public BookImpl addEntry(String languageCode, String id, Entry entry) {
        entries.get(languageCode).put(id, entry);
        return this;
    }

    public static final Codec<Book> PERSISTENT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(Book::getId),
            ResourceLocation.CODEC.optionalFieldOf("type", Modopedia.id("classic")).forGetter(Book::getType),
            Codec.STRING.fieldOf("title").forGetter(Book::getTitle),
            Codec.STRING.optionalFieldOf("subtitle", null).forGetter(Book::getSubtitle),
            Codec.STRING.optionalFieldOf("landingText", null).forGetter(Book::getRawLandingText),
            ResourceLocation.CODEC.optionalFieldOf("texture", Modopedia.id("gui/book/default")).forGetter(Book::getTexture),
            ResourceLocation.CODEC.optionalFieldOf("model", Modopedia.id("item/book_default")).forGetter(Book::getItemModelLocation),
            Codec.STRING.optionalFieldOf("defaultLanguage", "en_us").forGetter(Book::getDefaultLanguageCode)
    ).apply(instance, BookImpl::new));

    public static final StreamCodec<ByteBuf, Book> STREAM_CODEC = new StreamCodec<>() { // StreamCodec.composite overloads were too small :(

        @Override
        public Book decode(ByteBuf buf) {
            return new BookImpl(
                    ResourceLocation.STREAM_CODEC.decode(buf),
                    ResourceLocation.STREAM_CODEC.decode(buf),
                    ByteBufCodecs.STRING_UTF8.decode(buf),
                    ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8).decode(buf).orElse(null),
                    ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8).decode(buf).orElse(null),
                    ResourceLocation.STREAM_CODEC.decode(buf),
                    ResourceLocation.STREAM_CODEC.decode(buf),
                    ByteBufCodecs.STRING_UTF8.decode(buf)
            );
        }

        @Override
        public void encode(ByteBuf buf, Book book) {
            ResourceLocation.STREAM_CODEC.encode(buf, book.getId());
            ResourceLocation.STREAM_CODEC.encode(buf, book.getType());
            ByteBufCodecs.STRING_UTF8.encode(buf, book.getTitle());
            ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8).encode(buf, Optional.ofNullable(book.getSubtitle()));
            ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8).encode(buf, Optional.ofNullable(book.getRawLandingText()));
            ResourceLocation.STREAM_CODEC.encode(buf, book.getTexture());
            ResourceLocation.STREAM_CODEC.encode(buf, book.getItemModelLocation());
            ByteBufCodecs.STRING_UTF8.encode(buf, book.getDefaultLanguageCode());
        }

    };

}