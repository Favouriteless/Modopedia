package net.favouriteless.modopedia.api.books;

import com.mojang.serialization.Codec;
import net.favouriteless.modopedia.api.BookRenderContext;
import net.favouriteless.modopedia.api.PageComponentRegistry;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

/**
 * <p>
 *     Interface for custom component implementations; an instance of the component will be created for every time it
 *     is present on a page-- storing data here is fine.
 * </p>
 *  <b>IMPORTANT:</b> Components need to have a serializer registered via
 *  {@link PageComponentRegistry#register(ResourceLocation, Codec)}, otherwise Modopedia will not load it.
 *
 */
public interface Component {

    /**
     * Called when the Component is first created; use this for any setup needed directly after deserialization.
     *
     * @param x X position of this Component in pixels (relative to the page).
     * @param y Y position of this Component in pixels (relative to the page).
     * @param pageNum Index of the page this Component is on within the template.
     *
     */
    void init(Book book, int x, int y, int pageNum);

    /**
     * Called every render frame. The pose is transformed so that (0, 0) is the top left corner of the page.
     * You are responsible for all other transformations.
     */
    void render(GuiGraphics graphics, BookRenderContext context, int xMouse, int yMouse, float partialTicks);

}
