package net.favouriteless.modopedia.platform.services;

import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class NeoClientRegistryHelper implements IClientRegistryHelper {

	public static final List<PreparableReloadListener> RELOAD_LISTENERS = new ArrayList<>();

	@Override
	public void register(Item item, ResourceLocation location, ClampedItemPropertyFunction function) {
		ItemProperties.register(item, location, function);
	}

	@Override
	public PreparableReloadListener registerReloadListener(ResourceLocation id, PreparableReloadListener loader) {
		RELOAD_LISTENERS.add(loader);
		return loader;
	}

}
