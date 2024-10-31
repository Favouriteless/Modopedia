package net.favouriteless.modopedia;

import net.favouriteless.modopedia.api.BookRegistry;
import net.favouriteless.modopedia.api.ModopediaApi;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookContent;
import net.favouriteless.modopedia.api.books.BookContentManager;
import net.favouriteless.modopedia.api.books.TemplateProcessor;
import net.favouriteless.modopedia.api.text.TextFormatter;
import net.favouriteless.modopedia.book.TemplateRegistry;
import net.favouriteless.modopedia.book.text.TextFormatterRegistry;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class ModopediaApiImpl implements ModopediaApi {

    public static final ModopediaApi INSTANCE = new ModopediaApiImpl();

    private ModopediaApiImpl() {}

    @Override
    public @Nullable Book getBook(ResourceLocation id) {
        return BookRegistry.get().getBook(id);
    }

    @Override
    public @Nullable BookContent getBookContent(ResourceLocation id) {
         return BookContentManager.get().getContent(id);
    }

    @Override
    public void registerTemplateProcessor(ResourceLocation id, TemplateProcessor processor) {
        TemplateRegistry.createTemplate(id, processor);
    }

    @Override
    public void registerTextFormatter(TextFormatter formatter) {
        TextFormatterRegistry.register(formatter);
    }

}
