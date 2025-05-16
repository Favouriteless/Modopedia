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
    public @Nullable LocalisedBookContent getContent(String language) {
        if(content.containsKey(language))
            return content.get(language);
        else if(content.containsKey("en_us"))
            return content.get("en_us");

        return content.values().stream().findFirst().orElse(null);
    }

    @Override
    public Collection<LocalisedBookContent> getAllContents() {
        return content.values();
    }

    @Override
    public Collection<String> getLanguages() {
        return content.keySet();
    }

    public record LocalisedBookContentImpl(Map<String, Category> categories,
                                           Map<String, Entry> entries) implements LocalisedBookContent {

        public static LocalisedBookContentImpl create() {
            return new LocalisedBookContentImpl(new HashMap<>(), new HashMap<>());
        }

        @Override
        public @Nullable Category getCategory(String id) {
            return categories.get(id);
        }

        @Override
        public boolean hasCategory(String id) {
            return categories.containsKey(id);
        }

        @Override
        public @Nullable Entry getEntry(String id) {
            return entries.get(id);
        }

        @Override
        public boolean hasEntry(String id) {
            return entries.containsKey(id);
        }

        @Override
        public Collection<Entry> getEntries() {
            return entries.values();
        }

        @Override
        public Collection<Category> getCategories() {
            return categories.values();
        }

        @Override
        public Collection<String> getCategoryIds() {
            return categories.keySet();
        }

        @Override
        public Collection<String> getEntryIds() {
            return entries.keySet();
        }

    }

}
