package net.favouriteless.modopedia.book;

import net.favouriteless.modopedia.api.books.BookContent;
import net.favouriteless.modopedia.api.books.Category;
import net.favouriteless.modopedia.api.books.Entry;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BookContentImpl implements BookContent {

    private final Map<String, LocalisedBookContent> content;

    public BookContentImpl(Map<String, LocalisedBookContent> content) {
        this.content = content;
    }

    @Override
    public @Nullable Entry getEntry(String id, String language) {
        LocalisedBookContent localised = content.get(language);
        if(localised == null)
            return null;

        return localised.entries.get(id);
    }

    @Override
    public @Nullable Category getCategory(String id, String language) {
        LocalisedBookContent localised = content.get(language);
        if(localised == null)
            return null;

        return localised.categories.get(id);
    }

    @Override
    public Collection<Entry> getEntries(String language) {
        return content.containsKey(language) ? content.get(language).entries.values() : null;
    }

    @Override
    public Collection<Category> getCategories(String language) {
        return content.containsKey(language) ? content.get(language).categories.values() : null;
    }



    public record LocalisedBookContent(Map<String, Category> categories, Map<String, Entry> entries) {

        public static LocalisedBookContent create() {
            return new LocalisedBookContent(new HashMap<>(), new HashMap<>());
        }

    }

}
