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

    private final ResourceLocation type;
    private final String title;
    private final String subtitle;
    private final String rawLandingText;
    private final Component landingText;
    private final ResourceLocation texture;
    private final ResourceLocation itemModel;

    private final Map<String, Category> categories = new HashMap<>();
    private final Map<String, Entry> entries = new HashMap<>();

    public BookImpl(ResourceLocation type, String title, String subtitle, String rawLandingText,
                    ResourceLocation texture, ResourceLocation itemModel) {
        this.type = type;
        this.title = title;
        this.subtitle = subtitle;
        this.rawLandingText = rawLandingText;
        this.landingText = Component.literal(rawLandingText);
        this.texture = texture;
        this.itemModel = itemModel;
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

    @Nullable
    @Override
    public Category getCategory(String id) {
        return categories.get(id);
    }

    @Nullable
    @Override
    public Entry getEntry(String id) {
        return entries.get(id);
    }
    
    // --------------------------------- Below this point is non-API functions ---------------------------------
    
    public BookImpl addCategory(Category... categories) {
        for(Category category : categories) {
            this.categories.put(category.getId(), category);
        }
        return this;
    }

    public BookImpl addEntry(Entry... entries) {
        for(Entry entry : entries) {
            this.entries.put(entry.getId(), entry);
        }
        return this;
    }

    public static final Codec<Book> PERSISTENT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.optionalFieldOf("type", Modopedia.id("classic")).forGetter(Book::getType),
            Codec.STRING.fieldOf("title").forGetter(Book::getTitle),
            Codec.STRING.optionalFieldOf("subtitle", null).forGetter(Book::getSubtitle),
            Codec.STRING.optionalFieldOf("landingText", null).forGetter(Book::getRawLandingText),
            ResourceLocation.CODEC.optionalFieldOf("texture", Modopedia.id("gui/book/default")).forGetter(Book::getTexture),
            ResourceLocation.CODEC.optionalFieldOf("model", Modopedia.id("item/book_default")).forGetter(Book::getItemModelLocation)
    ).apply(instance, BookImpl::new));
    
    public static final StreamCodec<ByteBuf, Book> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, Book::getType,
            ByteBufCodecs.STRING_UTF8, Book::getTitle,
            ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8), book -> Optional.ofNullable(book.getSubtitle()),
            ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8), book -> Optional.ofNullable(book.getRawLandingText()),
            ResourceLocation.STREAM_CODEC, Book::getTexture,
            ResourceLocation.STREAM_CODEC, Book::getItemModelLocation,
            (type, title, subtitle, landingText, texture, model) -> new BookImpl(type, title,
                                                                                 subtitle.orElse(null),
                                                                                 landingText.orElse(null),
                                                                                 texture, model)
    );

}