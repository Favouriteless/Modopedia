package net.favouriteless.modopedia.book;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.api.books.Page;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EntryImpl implements Entry {

    private final String title;
    private final ItemStack iconStack;
    private final List<ResourceLocation> assignedItems;
    private final List<Page> pages = new ArrayList<>();

    public EntryImpl(String title, ItemStack iconStack, List<ResourceLocation> assignedItems) {
        this.title = title;
        this.iconStack = iconStack;
        this.assignedItems = assignedItems;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    @Nullable
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

    // ------------------------------------ Below this point is non-API functions ------------------------------------

    public Entry addPages(List<Page> pages) {
        this.pages.addAll(pages);
        return this;
    }

    // This isn't the only deserialization done, but the rest is near impossible to do as a codec.
    public static final Codec<EntryImpl> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("title").forGetter(Entry::getTitle),
            ItemStack.CODEC.optionalFieldOf("icon", Items.GRASS_BLOCK.getDefaultInstance()).forGetter(EntryImpl::getIcon),
            ResourceLocation.CODEC.listOf().optionalFieldOf("assigned_item").forGetter(e -> Optional.ofNullable(e.getAssignedItems()))
    ).apply(instance, (title, icon, assignedItem) -> new EntryImpl(title, icon, assignedItem.orElse(List.of()))));
    
}
