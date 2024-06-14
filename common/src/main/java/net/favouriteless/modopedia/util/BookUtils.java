package net.favouriteless.modopedia.util;

import net.favouriteless.modopedia.api.books.Category;
import net.favouriteless.modopedia.api.books.Entry;

import java.util.Collection;

public class BookUtils {

    public static Entry findEntry(String id, Collection<Entry> entries) {
        for(Entry entry : entries) {
            if(entry.getId().equals(id))
                return entry;
        }
        return null;
    }

    public static Category findCategory(String id, Collection<Category> categories) {
        for(Category category : categories) {
            if(category.getId().equals(id))
                return category;
        }
        return null;
    }

}
