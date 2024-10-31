package net.favouriteless.modopedia.api;

import net.favouriteless.modopedia.ModopediaApiImpl;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookContent;
import net.favouriteless.modopedia.api.books.TemplateProcessor;
import net.favouriteless.modopedia.api.text.TextFormatter;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public interface ModopediaApi {

    static ModopediaApi get() {
        return ModopediaApiImpl.INSTANCE;
    }

    /**
     * @return Book matching id, or null if none were found. (Forwards call to {@link BookRegistry#getBook(ResourceLocation)}
     */
    @Nullable Book getBook(ResourceLocation id);

    /**
     * @return BookContent matching id, or null if none were found. (Forwards call to {@link BookContentManager#getContent(ResourceLocation)})
     */
    @Nullable BookContent getBookContent(ResourceLocation id);

    void registerTemplateProcessor(ResourceLocation id, TemplateProcessor processor);

    void registerTextFormatter(TextFormatter formatter);

}
