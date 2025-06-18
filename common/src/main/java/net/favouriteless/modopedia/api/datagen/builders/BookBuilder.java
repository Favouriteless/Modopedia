package net.favouriteless.modopedia.api.datagen.builders;

import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookType;
import net.favouriteless.modopedia.book.BookImpl;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;

import java.util.function.BiConsumer;

public class BookBuilder {

    private final ResourceLocation id;
    private final String title;

    private String subtitle;
    private String rawLandingText;
    private BookType type = BookImpl.DEFAULT_TYPE;
    private ResourceKey<CreativeModeTab> tab;
    private ResourceLocation texture = BookImpl.DEFAULT_TEXTURE;
    private ResourceLocation itemModel = BookImpl.DEFAULT_ITEM_MODEL;
    private Holder<SoundEvent> openSound = BookImpl.DEFAULT_OPEN_SOUND;
    private Holder<SoundEvent> flipSound = BookImpl.DEFAULT_FLIP_SOUND;
    private ResourceLocation font = BookImpl.DEFAULT_FONT;
    private int textColour = BookImpl.DEFAULT_TEXT_COLOUR;
    private int headerColour = BookImpl.DEFAULT_HEADER_COLOUR;
    private int lineWidth = BookImpl.DEFAULT_LINE_WIDTH;

    private BookBuilder(ResourceLocation id, String title) {
        this.id = id;
        this.title = title;
    }

    public static BookBuilder of(ResourceLocation id, String title) {
        return new BookBuilder(id, title);
    }

    public BookBuilder subtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public BookBuilder landingText(String landingText) {
        this.rawLandingText = landingText;
        return this;
    }

    public BookBuilder type(BookType type) {
        this.type = type;
        return this;
    }

    public BookBuilder tab(ResourceKey<CreativeModeTab> tab) {
        this.tab = tab;
        return this;
    }

    public BookBuilder tab(ResourceLocation tab) {
        return tab(ResourceKey.create(Registries.CREATIVE_MODE_TAB, tab));
    }

    public BookBuilder texture(ResourceLocation texture) {
        this.texture = texture;
        return this;
    }

    public BookBuilder itemModel(ResourceLocation model) {
        this.itemModel = model;
        return this;
    }

    public BookBuilder openSound(Holder<SoundEvent> sound) {
        this.openSound = sound;
        return this;
    }

    public BookBuilder flipSound(Holder<SoundEvent> sound) {
        this.flipSound = sound;
        return this;
    }

    public BookBuilder font(ResourceLocation font) {
        this.font = font;
        return this;
    }

    public BookBuilder textColour(int colour) {
        this.textColour = colour;
        return this;
    }

    public BookBuilder headerColour(int colour) {
        this.headerColour = colour;
        return this;
    }

    public BookBuilder lineWidth(int width) {
        this.lineWidth = width;
        return this;
    }

    public void build(BiConsumer<ResourceLocation, Book> output) {
        output.accept(id, new BookImpl(title, subtitle, type, rawLandingText, texture, itemModel, tab, openSound, flipSound, font, textColour, headerColour, lineWidth));
    }

}
