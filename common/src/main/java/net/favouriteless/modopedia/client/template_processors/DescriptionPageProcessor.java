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

public class DescriptionPageProcessor implements TemplateProcessor {

    public static final ResourceLocation ID = Modopedia.id("description_page");

    @Override
    public void init(Book book, MutableLookup lookup, Level level) {
        BookTexture texture = BookTextureRegistry.get().getTexture(book.getTexture());
        Rectangle page = texture.pages().get(lookup.get("page_num").asInt() % texture.pages().size());

        int height = lookup.get("height").asInt();
        int yOff = lookup.getOrDefault("text_offset", 0).asInt();

        lookup.set("p_width", Variable.of(page.width()));
        lookup.set("p_text_y", Variable.of(height + yOff));
    }

}
