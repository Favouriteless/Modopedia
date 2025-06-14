package net.favouriteless.modopedia.platform.services;

import net.favouriteless.modopedia.Modopedia;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class NeoCommonRegistryHelper implements ICommonRegistryHelper {

	// Usually would allocate defregs as needed, but Modopedia only has a couple anyway :)
	public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(BuiltInRegistries.ITEM, Modopedia.MOD_ID);
	public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_REGISTRY = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, Modopedia.MOD_ID);
	public static final DeferredRegister<SoundEvent> SOUND_EVENT_REGISTRY = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Modopedia.MOD_ID);

	public static final List<PreparableReloadListener> RELOAD_LISTENERS = new ArrayList<>();

	@Override
	public <T extends Item> Supplier<T> registerItem(String name, Supplier<T> entry) {
		return ITEM_REGISTRY.register(name, entry);
	}

	@Override
	public <T extends DataComponentType<C>, C> Supplier<T> registerDataComponent(String name, Supplier<T> entry) {
		return DATA_COMPONENT_REGISTRY.register(name, entry);
	}

	@Override
	public <T extends SoundEvent> Supplier<T> registerSound(String name, Supplier<T> entry) {
		return SOUND_EVENT_REGISTRY.register(name, entry);
	}

	@Override
	public PreparableReloadListener registerReloadListener(ResourceLocation id, PreparableReloadListener loader) {
		RELOAD_LISTENERS.add(loader);
		return loader;
	}

}
