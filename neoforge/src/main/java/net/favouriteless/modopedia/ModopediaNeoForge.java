package net.favouriteless.modopedia;

import net.favouriteless.modopedia.platform.services.NeoForgeCommonRegistryHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(Modopedia.MOD_ID)
public class ModopediaNeoForge {

    public ModopediaNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        NeoForgeCommonRegistryHelper.ITEM_REGISTRY.register(modEventBus);
        NeoForgeCommonRegistryHelper.DATA_COMPONENT_REGISTRY.register(modEventBus);
        Modopedia.init();
    }

}
