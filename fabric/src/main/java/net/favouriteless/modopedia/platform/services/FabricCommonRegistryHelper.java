package net.favouriteless.modopedia.platform.services;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.platform.JsonDataLoaderWrapper;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class FabricCommonRegistryHelper implements ICommonRegistryHelper {

	@Override
	public <T extends Item> Supplier<T> registerItem(String name, Supplier<T> entry) {
		T value = entry.get();
		Registry.register(BuiltInRegistries.ITEM, Modopedia.id(name), value);
		return () -> value;
	}

	@Override
	public <T extends DataComponentType<C>, C> Supplier<T> registerDataComponent(String name, Supplier<T> entry) {
		T value = entry.get();
		Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Modopedia.id(name), value);
		return () -> value;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends SoundEvent> Holder<T> registerSound(String name, Supplier<T> entry) {
		return (Holder<T>)Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, Modopedia.id(name), entry.get());
	}

	@Override
	public PreparableReloadListener registerReloadListener(ResourceLocation id, PreparableReloadListener loader) {
		ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new JsonDataLoaderWrapper(id, loader)); // Fabric impl adds a wrapper for loaders.
		return loader;
	}

}
