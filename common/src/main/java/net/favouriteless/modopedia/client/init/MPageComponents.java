package net.favouriteless.modopedia.client.init;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.registries.PageComponentRegistry;
import net.favouriteless.modopedia.client.page_components.*;

public class MPageComponents {

    public static void load() {
        PageComponentRegistry registry = PageComponentRegistry.get();

        registry.register(Modopedia.id("text"), TextPageComponent::new);
        registry.register(Modopedia.id("image"), ImagePageComponent::new);
        registry.register(Modopedia.id("tooltip"), TooltipPageComponent::new);
        registry.register(Modopedia.id("header"), HeaderPageComponent::new);
        registry.register(Modopedia.id("separator"), SeparatorPageComponent::new);
        registry.register(Modopedia.id("item"), ItemPageComponent::new);
        registry.register(Modopedia.id("multiblock"), MultiblockPageComponent::new);
        registry.register(Modopedia.id("small_frame"), () -> new WidgetPageComponent("small_frame"));
        registry.register(Modopedia.id("medium_frame"), () -> new WidgetPageComponent("medium_frame"));
        registry.register(Modopedia.id("large_frame"), () -> new WidgetPageComponent("large_frame"));
        registry.register(Modopedia.id("crafting_grid"), () -> new WidgetPageComponent("crafting_grid"));
        registry.register(Modopedia.id("crafting_arrow"), () -> new WidgetPageComponent("crafting_arrow"));
        registry.register(Modopedia.id("crafting_flame"), () -> new WidgetPageComponent("crafting_flame"));
        registry.register(Modopedia.id("entity"), EntityPageComponent::new);
    }

}
