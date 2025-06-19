package net.favouriteless.modopedia.book;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.Category;
import net.favouriteless.modopedia.book.text.Justify;
import net.favouriteless.modopedia.book.text.TextChunk;
import net.favouriteless.modopedia.book.text.TextParser;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class CategoryImpl implements Category {

    public static final boolean DEFAULT_DISPLAY_ON_FRONT_PAGE = true;
    public static final Supplier<ItemStack> DEFAULT_ICON = Items.GRASS_BLOCK::getDefaultInstance;

    private final String title;
    private final String rawLandingText;
    private final ItemStack iconStack;
    private final boolean displayFrontPage;
    private final ResourceLocation advancement;
    private final List<String> entries;
    private final List<String> children;

    private List<TextChunk> landingText = null; // Fields here get built after the constructor runs. They aren't encoded ever.

    public CategoryImpl(String title, String rawLandingText, ItemStack iconStack, List<String> entries,
                        List<String> children, boolean displayFrontPage, ResourceLocation advancement) {
        this.title = title;
        this.rawLandingText = rawLandingText;
        this.iconStack = iconStack;
        this.entries = entries;
        this.children = children;
        this.displayFrontPage = displayFrontPage;
        this.advancement = advancement;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public List<TextChunk> getLandingText() {
        return landingText;
    }

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

    @Override
    public ResourceLocation getAdvancement() {
        return advancement;
    }

    public CategoryImpl init(Book book) {
        landingText = TextParser.parse(rawLandingText, book.getLineWidth(), Minecraft.getInstance().font.lineHeight,
                Justify.LEFT, Style.EMPTY.withFont(book.getFont()).withColor(book.getTextColour()));
        return this;
    }

    public static final Codec<Category> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("title").forGetter(Category::getTitle),
            Codec.STRING.optionalFieldOf("landing_text").forGetter(c -> Optional.ofNullable(c.getRawLandingText())),
            ItemStack.CODEC.optionalFieldOf("icon", DEFAULT_ICON.get()).forGetter(Category::getIcon),
            Codec.STRING.listOf().optionalFieldOf("entries", new ArrayList<>()).forGetter(Category::getEntries),
            Codec.STRING.listOf().optionalFieldOf("children", new ArrayList<>()).forGetter(Category::getChildren),
            Codec.BOOL.optionalFieldOf("display_on_front_page", DEFAULT_DISPLAY_ON_FRONT_PAGE).forGetter(Category::getDisplayOnFrontPage),
            ResourceLocation.CODEC.optionalFieldOf("advancement").forGetter(c -> Optional.ofNullable(c.getAdvancement()))
    ).apply(instance, (title, landingText, iconStack, entries, children, displayFront, advancement) -> new CategoryImpl(
            title, landingText.orElse(null), iconStack, entries, children, displayFront, advancement.orElse(null)))
    );

}
