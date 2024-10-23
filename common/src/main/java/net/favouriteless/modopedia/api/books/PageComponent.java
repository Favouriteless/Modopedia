package net.favouriteless.modopedia.api.books;

import com.mojang.serialization.MapCodec;
import net.favouriteless.modopedia.api.PageComponentRegistry;
import net.favouriteless.modopedia.book.components.PositionedPageComponent;
import net.favouriteless.modopedia.book.PageComponentRegistryImpl.PageComponentType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

/**
 * <p>
 *     Interface for custom component implementations; an instance of the component will be created for every time it
 *     is present on a page-- storing data here is fine.
 * </p>
 * <p>
 *  <b>IMPORTANT:</b> Components need to have a serializer registered via
 *  {@link PageComponentRegistry#register(ResourceLocation, MapCodec)}, otherwise Modopedia will not load it.
 * </p>
 * See: {@link PositionedPageComponent} for the basic implementation used for all the default components.
 */
public interface PageComponent {

    /**
     * Called when the Component is first created; use this for any setup needed directly after deserialization.
     */
    void init(int pageNum);

    /**
     * Called every render frame. The pose is transformed so that (0, 0) is the top left corner of the page.
     * You are responsible for all other transformations.
     */
    void render(GuiGraphics graphics, BookRenderContext context, int xMouse, int yMouse, float partialTicks);

    /**
     * @return This component's {@link PageComponentType}, this is obtained via
     * {@link PageComponentRegistry#register(ResourceLocation, MapCodec)}.
     */
    PageComponentType type();

}
