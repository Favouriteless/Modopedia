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

        return localised.getEntry(id);
    }

    @Override
    public @Nullable Category getCategory(String id, String language) {
        LocalisedBookContent localised = content.get(language);
        if(localised == null)
            return null;

        return localised.getCategory(id);
    }

    @Override
    public @Nullable LocalisedBookContent getLocalisedContent(String language) {
        if(content.containsKey(language))
            return content.get(language);
        else if(content.containsKey("en_us"))
            return content.get("en_us");

        return content.values().stream().findFirst().orElse(null);
    }

    @Override
    public Collection<LocalisedBookContent> getLocalisedContents() {
        return content.values();
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
        public @Nullable Entry getEntry(String id) {
            return entries.get(id);
        }

    }

}
