package net.favouriteless.modopedia.platform.services;

import net.favouriteless.modopedia.Modopedia;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class NeoForgeCommonRegistryHelper implements ICommonRegistryHelper {

	// Usually would allocate defregs as needed, but Modopedia only has items anyway :)
	public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(BuiltInRegistries.ITEM, Modopedia.MOD_ID);
	public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_REGISTRY = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, Modopedia.MOD_ID);

	public static final List<SimpleJsonResourceReloadListener> dataLoaders = new ArrayList<>();

	@Override
	public <T extends Item> Supplier<T> registerItem(String name, Supplier<T> entry) {
		return ITEM_REGISTRY.register(name, entry);
	}

	@Override
	public <T extends DataComponentType<C>, C> Supplier<T> registerDataComponent(String name, Supplier<T> entry) {
		return DATA_COMPONENT_REGISTRY.register(name, entry);
	}

	@Override
	public void registerReloadListener(ResourceLocation id, SimpleJsonResourceReloadListener loader) {
		dataLoaders.add(loader);
	}

}
