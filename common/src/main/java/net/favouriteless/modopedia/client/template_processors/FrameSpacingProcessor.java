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

public class FrameSpacingProcessor implements TemplateProcessor {

    public static final ResourceLocation ID_SMALL = Modopedia.id("small_frame_spacing");
    public static final ResourceLocation ID_MEDIUM = Modopedia.id("medium_frame_spacing");
    public static final ResourceLocation ID_LARGE = Modopedia.id("large_frame_spacing");
    public static final ResourceLocation ID_CRAFTING = Modopedia.id("crafting_grid_spacing");

    protected final String widgetId;

    public FrameSpacingProcessor(String widgetId) {
        this.widgetId = widgetId;
    }

    @Override
    public void init(Book book, MutableLookup lookup, Level level) {
        BookTexture tex = BookTextureRegistry.get().getTexture(book.getTexture());
        if(tex == null)
            throw new IllegalStateException("Book is missing a valid BookTexture");

        Rectangle rect = tex.widgets().get(widgetId);
        if(rect == null)
            throw new IllegalStateException("Frame spacing processor cannot find widget: " + widgetId);


        int width = lookup.get("width").asInt();
        lookup.set("frame_offset", Variable.of(-(rect.width() - width) / 2));
    }

}
