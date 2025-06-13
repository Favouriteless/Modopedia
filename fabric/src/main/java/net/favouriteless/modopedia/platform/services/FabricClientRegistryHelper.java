package net.favouriteless.modopedia.platform.services;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.favouriteless.modopedia.platform.JsonDataLoaderWrapper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.item.Item;

public class FabricClientRegistryHelper implements IClientRegistryHelper {

	@Override
	public void register(Item item, ResourceLocation location, ClampedItemPropertyFunction function) {
		ItemProperties.register(item, location, function);
	}

	@Override
	public PreparableReloadListener registerReloadListener(ResourceLocation id, PreparableReloadListener loader) {
		ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new JsonDataLoaderWrapper(id, loader));
		return loader;
	}

	@Override
	public KeyMapping register(String name, int keyCode, String category, KeyConflictContext conflictContext) {
		return KeyBindingHelper.registerKeyBinding(new KeyMapping(name, keyCode, category));
	}

}
