package net.favouriteless.modopedia.book;

import net.favouriteless.modopedia.api.BookRegistry;
import net.favouriteless.modopedia.api.books.*;
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
            return getEntry(id);

        return localised.entries.getOrDefault(id, getEntry(id));
    }

    @Override
    public @Nullable Entry getEntry(String id) {
        return getDefaultLocalisation().entries().get(id);
    }

    @Override
    public @Nullable Category getCategory(String id, String language) {
        LocalisedBookContent localised = content.get(language);
        if(localised == null)
            return getCategory(id);

        return localised.categories.getOrDefault(id, getCategory(id));
    }

    @Override
    public @Nullable Category getCategory(String id) {
        return getDefaultLocalisation().categories.get(id);
    }

    @Override
    public Collection<Entry> getEntries(String language) {
        return content.containsKey(language) ? content.get(language).entries.values() : null;
    }

    @Override
    public Collection<Category> getCategories(String language) {
        return content.containsKey(language) ? content.get(language).categories.values() : null;
    }

    private LocalisedBookContent getDefaultLocalisation() {
        Book book = BookRegistry.get().getBook(BookContentManager.get().getBookId(this));
        String langCode = book != null ? book.getDefaultLanguage() : "en_us";
        return content.computeIfAbsent(langCode, k -> new LocalisedBookContent(Map.of(), Map.of()));
    }

    public record LocalisedBookContent(Map<String, Category> categories, Map<String, Entry> entries) {

        public static LocalisedBookContent create() {
            return new LocalisedBookContent(new HashMap<>(), new HashMap<>());
        }

    }

}
