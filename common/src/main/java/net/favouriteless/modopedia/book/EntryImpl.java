package net.favouriteless.modopedia.book;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.api.book.Entry;
import net.favouriteless.modopedia.api.book.Page;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class EntryImpl implements Entry {

    public static final Supplier<ItemStack> DEFAULT_ICON = Items.GRASS_BLOCK::getDefaultInstance;

    private final String title;
    private final ItemStack iconStack;
    private final List<ResourceLocation> assignedItems;
    private final ResourceLocation advancement;
    private final List<Page> pages = new ArrayList<>();

    public EntryImpl(String title, ItemStack iconStack, List<ResourceLocation> assignedItems, ResourceLocation advancement) {
        this.title = title;
        this.iconStack = iconStack;
        this.assignedItems = assignedItems;
        this.advancement = advancement;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public ItemStack getIcon() {
        return iconStack;
    }

    @Override
    public List<Page> getPages() {
        return pages;
    }

    @Override
    public List<ResourceLocation> getAssignedItems() {
        return assignedItems;
    }

    @Override
    public ResourceLocation getAdvancement() {
        return advancement;
    }

    // ------------------------------------ Below this point is non-API functions ------------------------------------

    public Entry addPages(List<Page> pages) {
        this.pages.addAll(pages);
        return this;
    }

    // This isn't the only deserialization done, but the rest is near impossible to do as a codec.
    public static final Codec<Entry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("title").forGetter(Entry::getTitle),
            ItemStack.CODEC.optionalFieldOf("icon", DEFAULT_ICON.get()).forGetter(Entry::getIcon),
            ResourceLocation.CODEC.listOf().optionalFieldOf("assigned_items").forGetter(e -> Optional.ofNullable(e.getAssignedItems())),
            ResourceLocation.CODEC.optionalFieldOf("advancement").forGetter(c -> Optional.ofNullable(c.getAdvancement()))
    ).apply(instance, (title, icon, assignedItem, advancement) -> new EntryImpl(
            title, icon, assignedItem.orElse(List.of()), advancement.orElse(null)))
    );
    
}
