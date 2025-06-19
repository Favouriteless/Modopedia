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

/**
 * Vertically centers a number of same-size components on a page, with or without a header.
 */
public class VerticalSpacingProcessor implements TemplateProcessor {

    public static final ResourceLocation ID = Modopedia.id("vertical_spacing");

    @Override
    public void init(Book book, MutableLookup lookup, Level level) {
        BookTexture texture = BookTextureRegistry.get().getTexture(book.getTexture());
        Rectangle page = texture.pages().get(lookup.get("page_num").asInt() % texture.pages().size());

        int headerSize = texture.widgets().containsKey("separator") ? 10 + texture.widgets().get("separator").height() : 10;

        int padding = lookup.getOrDefault("with_header", false).asBoolean() ? headerSize : 0;
        int items = lookup.get("vertical_items").asInt();

        int size = lookup.has("vertical_size_widget") ? texture.widgets().get(lookup.get("vertical_size_widget").asString()).height() : lookup.get("vertical_size").asInt();
        int spacing = lookup.getOrDefault("vertical_spacing", 5).asInt();

        int height = items*size + (items-1)*spacing;
        int offset = (page.height() - padding - height) / 2 + padding;

        for(int i = 0; i < items; i++) {
            lookup.set("p_vertical_y" + (i+1), Variable.of(offset + (size + spacing) * i));
        }
    }

}
