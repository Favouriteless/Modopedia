package net.favouriteless.modopedia.client.init;

import net.favouriteless.modopedia.api.registries.client.ItemDisplayRegistry;
import net.favouriteless.modopedia.client.page_components.item_displays.*;

public class MItemDisplays {

    public static void load() {
        ItemDisplayRegistry registry = ItemDisplayRegistry.get();

        registry.register(SimpleItemDisplay.ID, SimpleItemDisplay.CODEC);
        registry.register(CyclingItemDisplay.ID, CyclingItemDisplay.CODEC);
        registry.register(TagItemDisplay.ID, TagItemDisplay.CODEC);
        registry.register(GridItemDisplay.ID, GridItemDisplay.CODEC);
        registry.register(RingsItemDisplay.ID, RingsItemDisplay.CODEC);
    }

}
