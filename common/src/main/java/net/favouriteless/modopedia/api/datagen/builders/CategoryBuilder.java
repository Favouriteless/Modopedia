package net.favouriteless.modopedia.api.datagen.builders;

import net.favouriteless.modopedia.api.datagen.CategoryOutput;
import net.favouriteless.modopedia.book.CategoryImpl;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class CategoryBuilder {

    private final String id;
    private final String title;

    private String rawLandingText;
    private ItemStack iconStack = CategoryImpl.DEFAULT_ICON.get();
    private boolean displayFrontPage = CategoryImpl.DEFAULT_DISPLAY_ON_FRONT_PAGE;
    private ResourceLocation advancement;
    private final List<String> entries = new ArrayList<>();
    private final List<String> children = new ArrayList<>();

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

    public void build(CategoryOutput output) {
        output.accept(id, new CategoryImpl(title, rawLandingText, iconStack, entries, children, displayFrontPage, advancement));
    }

}
