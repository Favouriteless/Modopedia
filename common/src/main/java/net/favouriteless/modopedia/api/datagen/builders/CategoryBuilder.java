package net.favouriteless.modopedia.api.datagen.builders;

import net.favouriteless.modopedia.api.book.Category;
import net.favouriteless.modopedia.book.CategoryImpl;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public class CategoryBuilder {

    private final String id;
    private final String title;

    private String rawLandingText;
    private ItemStack iconStack;
    private boolean displayFrontPage;
    private ResourceLocation advancement;
    private List<String> entries = new ArrayList<>();
    private List<String> children = new ArrayList<>();

    private CategoryBuilder(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public static CategoryBuilder of(String id, String title) {
        return new CategoryBuilder(id, title);
    }

    public CategoryBuilder landingText(String landingText) {
        this.rawLandingText = landingText;
        return this;
    }

    public CategoryBuilder icon(ItemStack icon) {
        this.iconStack = icon;
        return this;
    }

    public CategoryBuilder displayOnFrontPage(boolean value) {
        this.displayFrontPage = value;
        return this;
    }

    public CategoryBuilder advancement(ResourceLocation id) {
        this.advancement = id;
        return this;
    }

    public CategoryBuilder entries(String... entries) {
        this.entries.addAll(Arrays.asList(entries));
        return this;
    }

    public CategoryBuilder children(String... children) {
        this.children.addAll(Arrays.asList(children));
        return this;
    }

    public void build(BiConsumer<String, Category> output) {
        output.accept(id, new CategoryImpl(title, rawLandingText, iconStack, entries, children, displayFrontPage, advancement));
    }

}
