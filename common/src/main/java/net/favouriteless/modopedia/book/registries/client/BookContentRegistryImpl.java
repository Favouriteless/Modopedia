package net.favouriteless.modopedia.book.registries.client;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.favouriteless.modopedia.api.ModopediaApi.EntryAssociation;
import net.favouriteless.modopedia.api.book.BookContent;
import net.favouriteless.modopedia.api.book.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.book.Entry;
import net.favouriteless.modopedia.api.registries.client.BookContentRegistry;
import net.minecraft.resources.ResourceLocation;

public class BookContentRegistryImpl implements BookContentRegistry {

    public static final BookContentRegistryImpl INSTANCE = new BookContentRegistryImpl();

    private final BiMap<ResourceLocation, BookContent> bookContents = HashBiMap.create();

    private BookContentRegistryImpl() {}

    @Override
    public BookContent getContent(ResourceLocation id) {
        return bookContents.get(id);
    }

    @Override
    public LocalisedBookContent getContent(ResourceLocation id, String lang) {
        BookContent content = getContent(id);
        return content != null ? content.getContent(lang) : null;
    }

    @Override
    public ResourceLocation getBookId(BookContent content) {
        return bookContents.inverse().get(content);
    }

    @Override
    public void register(ResourceLocation id, BookContent content) {
        bookContents.put(id, content);

        ItemAssociationRegistry.removeBook(id);
        for(String lang : content.getLanguages()) {
            LocalisedBookContent local = content.getContent(lang);

            for(String entryId : local.getEntryIds()) {
                Entry entry = local.getEntry(entryId);
                for(ResourceLocation item : entry.getAssignedItems())
                    ItemAssociationRegistry.register(lang, item, new EntryAssociation(id, entryId, entry));
            }
        }
    }

}
