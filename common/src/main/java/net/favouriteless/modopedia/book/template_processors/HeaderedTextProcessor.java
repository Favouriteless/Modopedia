package net.favouriteless.modopedia.book.template_processors;

import net.favouriteless.modopedia.api.Lookup.MutableLookup;
import net.favouriteless.modopedia.api.Variable;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookTexture;
import net.favouriteless.modopedia.api.books.TemplateProcessor;
import net.favouriteless.modopedia.api.registries.BookTextureRegistry;
import net.minecraft.world.level.Level;

public class HeaderedTextProcessor implements TemplateProcessor {

    @Override
    public void init(Book book, MutableLookup lookup, Level level) {
        BookTexture tex = BookTextureRegistry.get().getTexture(book.getTexture());
        lookup.set("text_y", Variable.of(tex.separator().height() + 12));
    }

}
