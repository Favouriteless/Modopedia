package net.favouriteless.modopedia.client.template_processors;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.Lookup.MutableLookup;
import net.favouriteless.modopedia.api.Variable;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookTexture;
import net.favouriteless.modopedia.api.book.BookTexture.Rectangle;
import net.favouriteless.modopedia.api.book.TemplateProcessor;
import net.favouriteless.modopedia.api.registries.client.BookTextureRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class WidgetSpacingProcessor implements TemplateProcessor {

    public static final ResourceLocation ID = Modopedia.id("widget_spacing");

    public WidgetSpacingProcessor() {}

    @Override
    public void init(Book book, MutableLookup lookup, Level level) {
        BookTexture tex = BookTextureRegistry.get().getTexture(book.getTexture());
        if(tex == null)
            throw new IllegalStateException("Book is missing a valid BookTexture");

        String widget = lookup.get("widget").asString();
        int width = lookup.get("width").asInt();

        Rectangle rect = tex.widgets().get(widget);
        if(rect == null)
            throw new IllegalStateException("Frame spacing processor cannot find widget: " + widget);


        lookup.set("p_offset", Variable.of(-(rect.width() - width) / 2));
    }

}
