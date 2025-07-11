package net.favouriteless.modopedia.api.book.page_components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.favouriteless.modopedia.api.registries.client.ItemDisplayRegistry;
import net.favouriteless.modopedia.client.page_components.ItemGalleryPageComponent;
import net.minecraft.client.gui.GuiGraphics;

/**
 * Represents a type of item display for an {@link ItemGalleryPageComponent}.
 */
public interface ItemDisplay {

    /**
     * Render this ItemDisplay. PoseStack is already transformed so that (0, 0) is the position of the display and
     * mouse positions are relative to the page the display is on.
     */
    void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, String entry);

    /**
     * @return {@link MapCodec} for this type of ItemDisplay.
     */
    MapCodec<? extends ItemDisplay> typeCodec();

    static Codec<ItemDisplay> codec() {
        return ItemDisplayRegistry.get().codec();
    }

}
