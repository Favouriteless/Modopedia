package net.favouriteless.modopedia.platform.services;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public interface ICommonRegistryHelper {

    /**
     * Register an item into either A.) A vanilla registry (fabric) or B.) A deferred registry (neoforge).
     *
     * @param name The name of the object. This will automatically use Modopedia's namespace.
     * @param entry A supplier providing a *new* instance of the item.
     *
     * @return A {@link Supplier} providing the registered object.
     */
    <T extends Item> Supplier<T> registerItem(String name, Supplier<T> entry);

    /**
     * Register a data component type into either A.) A vanilla registry (fabric) or B.) A deferred registry (neoforge).
     *
     * @param name The name of the object. This will automatically use Modopedia's namespace.
     * @param entry A supplier providing a *new* instance of the item.
     *
     * @return A {@link Supplier} providing the registered object.
     */
    <T extends DataComponentType<C>, C> Supplier<T> registerDataComponent(String name, Supplier<T> entry);

    /**
     * Register a {@link PreparableReloadListener}, necessary because Fabric requires ReloadListeners to provide
     * an ID (god knows why).
     *
     * @param id {@link ResourceLocation} ID of the loader, only used by Fabric.
     * @param loader An instance of the ReloadListener.
     */
    PreparableReloadListener registerReloadListener(ResourceLocation id, PreparableReloadListener loader);

}
