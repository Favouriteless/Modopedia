package net.favouriteless.modopedia.client;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.favouriteless.modopedia.api.books.BookContent;
import net.favouriteless.modopedia.api.BookContentManager;
import net.minecraft.resources.ResourceLocation;

public class BookContentManagerImpl implements BookContentManager {

    public static final BookContentManager INSTANCE = new BookContentManagerImpl();

    private final BiMap<ResourceLocation, BookContent> bookContents = HashBiMap.create();

    private BookContentManagerImpl() {}

    @Override
    public BookContent getContent(ResourceLocation id) {
        return bookContents.get(id);
    }

    @Override
    public ResourceLocation getBookId(BookContent content) {
        return bookContents.inverse().get(content);
    }

    @Override
    public void register(ResourceLocation id, BookContent content) {
        bookContents.put(id, content);
    }

    @Override
    public void clear() {
        bookContents.clear();
    }

    @Override
    public void remove(ResourceLocation id) {
        bookContents.remove(id);
    }

}
