package net.favouriteless.modopedia.book;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.api.ModopediaApi;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.api.books.Page;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EntryImpl implements Entry {

    private ResourceLocation bookId; // This gets set after the Entry is instantiated because we don't want it to be present in the persistent codec.
    
    private final String title;
    private final ItemStack iconStack;
    private final List<Page> pages;

    public EntryImpl(String title, ItemStack iconStack, List<Page> pages) {
        this.title = title;
        this.iconStack = iconStack;
        this.pages = pages;
    }

    @Override
    public Book getBook() {
        return ModopediaApi.get().getBook(bookId);
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

    // ------------------------------------ Below this point is non-API functions ------------------------------------

    public static final Codec<Entry> PERSISTENT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("title").forGetter(Entry::getTitle),
            ItemStack.CODEC.fieldOf("iconStack").forGetter(Entry::getIcon),
            PageImpl.PERSISTENT_CODEC.listOf().fieldOf("pages").forGetter(Entry::getPages)
    ).apply(instance, EntryImpl::new));
    
}
