package net.favouriteless.modopedia.book;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.Category;
import net.favouriteless.modopedia.book.text.TextChunk;
import net.favouriteless.modopedia.book.text.TextParser;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryImpl implements Category {

    private final String title; // Fields here get set by the codec
    private final String subtitle;
    private final String rawLandingText;
    private final ItemStack iconStack;
    private final List<String> entries;
    private final List<String> children;

    private List<TextChunk> landingText = null; // Fields here get built after the constructor runs. They aren't encoded ever.

    public CategoryImpl(String title, String subtitle, String rawLandingText, ItemStack iconStack,
                        List<String> entries, List<String> children) {
        this.title = title;
        this.subtitle = subtitle;
        this.rawLandingText = rawLandingText;
        this.iconStack = iconStack;
        this.entries = entries;
        this.children = children;
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
    public List<TextChunk> getLandingText() {
        return landingText;
    }

    @Nullable
    @Override
    public String getRawLandingText() {
        return rawLandingText;
    }

    @Override
    public ItemStack getIcon() {
        return iconStack;
    }

    @Override
    public List<String> getEntries() {
        return entries;
    }

    @Override
    public List<String> getChildren() {
        return children;
    }

    // ------------------------------------ Below this point is non-API functions ------------------------------------

    public CategoryImpl init(Book book) {
        this.landingText = rawLandingText != null ?
                TextParser.parse(rawLandingText, book.getLineWidth(), 9, Style.EMPTY.withFont(book.getFont()).withColor(book.getTextColour()), 0, 0) :
                List.of();

        return this;
    }

    public static final Codec<CategoryImpl> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("title").forGetter(Category::getTitle),
            Codec.STRING.optionalFieldOf("subtitle").forGetter(c -> Optional.ofNullable(c.getSubtitle())),
            Codec.STRING.optionalFieldOf("landing_text").forGetter(c -> Optional.ofNullable(c.getRawLandingText())),
            ItemStack.CODEC.optionalFieldOf("icon").forGetter(c -> Optional.ofNullable(c.getIcon())),
            Codec.STRING.listOf().optionalFieldOf("entries", new ArrayList<>()).forGetter(Category::getEntries),
            Codec.STRING.listOf().optionalFieldOf("children", new ArrayList<>()).forGetter(Category::getChildren)
    ).apply(instance, (title, subtitle, landingText, iconStack, entries, children) ->
            new CategoryImpl(title, subtitle.orElse(null), landingText.orElse(null),
                             iconStack.orElse(null), entries, children))
    );

}
