package net.favouriteless.modopedia.api.datagen.builders;

import com.google.gson.JsonElement;
import net.favouriteless.modopedia.api.book.Category;
import net.favouriteless.modopedia.api.datagen.BookContentBuilder;
import net.favouriteless.modopedia.api.datagen.BookContentOutput;
import net.favouriteless.modopedia.book.CategoryImpl;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryBuilder implements BookContentBuilder {

    private final String title;

    private String rawLandingText;
    private ItemStack iconStack = CategoryImpl.DEFAULT_ICON.get();
    private boolean displayFrontPage = CategoryImpl.DEFAULT_DISPLAY_ON_FRONT_PAGE;
    private ResourceLocation advancement;
    private final List<String> entries = new ArrayList<>();
    private final List<String> children = new ArrayList<>();
    private int sortNum = 0;

    private CategoryBuilder(String title) {
        this.title = title;
    }

    public static CategoryBuilder of(String title) {
        return new CategoryBuilder(title);
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

    public CategoryBuilder sortNum(int value) {
        this.sortNum = value;
        return this;
    }

    @Override
    public JsonElement build(RegistryOps<JsonElement> ops) {
        return Category.codec().encodeStart(ops, new CategoryImpl(title, rawLandingText, iconStack, entries, children, displayFrontPage, advancement, sortNum)).getOrThrow();
    }

    public void build(String id, BookContentOutput output) {
        this.entries.addAll(output.getEntries(id));
        BookContentBuilder.super.build(id, output);
    }

}
