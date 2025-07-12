package net.favouriteless.modopedia.client.init;

import net.favouriteless.modopedia.api.registries.client.PageComponentRegistry;
import net.favouriteless.modopedia.client.page_components.*;

public class MPageComponents {

    public static void load() {
        PageComponentRegistry registry = PageComponentRegistry.get();

        registry.register(TextPageComponent.ID, TextPageComponent::new);
        registry.register(ImagePageComponent.ID, ImagePageComponent::new);
        registry.register(TooltipPageComponent.ID, TooltipPageComponent::new);
        registry.register(HeaderPageComponent.ID, HeaderPageComponent::new);
        registry.register(SeparatorPageComponent.ID, SeparatorPageComponent::new);
        registry.register(ItemPageComponent.ID, ItemPageComponent::new);
        registry.register(ItemGalleryPageComponent.ID, ItemGalleryPageComponent::new);
        registry.register(MultiblockPageComponent.ID, MultiblockPageComponent::new);
        registry.register(WidgetPageComponent.ID_SMALL_FRAME, () -> new WidgetPageComponent("small_frame"));
        registry.register(WidgetPageComponent.ID_MEDIUM_FRAME, () -> new WidgetPageComponent("medium_frame"));
        registry.register(WidgetPageComponent.ID_LARGE_FRAME, () -> new WidgetPageComponent("large_frame"));
        registry.register(WidgetPageComponent.ID_CRAFTING_GRID, () -> new WidgetPageComponent("crafting_grid"));
        registry.register(WidgetPageComponent.ID_CRAFTING_ARROW, () -> new WidgetPageComponent("crafting_arrow"));
        registry.register(WidgetPageComponent.ID_CRAFTING_FLAME, () -> new WidgetPageComponent("crafting_flame"));
        registry.register(EntityPageComponent.ID, EntityPageComponent::new);
        registry.register(ShowcasePageComponent.ID, ShowcasePageComponent::new);
    }

}
