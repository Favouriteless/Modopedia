package net.favouriteless.modopedia.book;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.Category;
import net.favouriteless.modopedia.book.text.Justify;
import net.favouriteless.modopedia.book.text.TextChunk;
import net.favouriteless.modopedia.book.text.TextParser;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryImpl implements Category {

    private final String title; // Fields here get set by the codec
    private final String rawLandingText;
    private final ItemStack iconStack;
    private final List<String> entries;
    private final List<String> children;
    private final boolean displayFrontPage;

    private List<TextChunk> landingText = null; // Fields here get built after the constructor runs. They aren't encoded ever.

    public CategoryImpl(String title, String rawLandingText, ItemStack iconStack,
                        List<String> entries, List<String> children, boolean displayFrontPage) {
        this.title = title;
        this.rawLandingText = rawLandingText;
        this.iconStack = iconStack;
        this.entries = entries;
        this.children = children;
        this.displayFrontPage = displayFrontPage;
    }

    @Override
    public String getTitle() {
        return title;
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

    @Override
    public boolean getDisplayOnFrontPage() {
        return displayFrontPage;
    }

    public CategoryImpl init(Book book) {
        landingText = TextParser.parse(rawLandingText, book.getLineWidth(), Minecraft.getInstance().font.lineHeight,
                Justify.LEFT, Style.EMPTY.withFont(book.getFont()).withColor(book.getTextColour()));
        return this;
    }

    public static final Codec<CategoryImpl> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("title").forGetter(Category::getTitle),
            Codec.STRING.optionalFieldOf("landing_text").forGetter(c -> Optional.ofNullable(c.getRawLandingText())),
            ItemStack.CODEC.optionalFieldOf("icon", Items.GRASS_BLOCK.getDefaultInstance()).forGetter(CategoryImpl::getIcon),
            Codec.STRING.listOf().optionalFieldOf("entries", new ArrayList<>()).forGetter(Category::getEntries),
            Codec.STRING.listOf().optionalFieldOf("children", new ArrayList<>()).forGetter(Category::getChildren),
            Codec.BOOL.optionalFieldOf("display_on_front_page", true).forGetter(Category::getDisplayOnFrontPage)
    ).apply(instance, (title, landingText, iconStack, entries, children, displayFront) ->
            new CategoryImpl(title, landingText.orElse(null), iconStack, entries, children, displayFront))
    );

}
