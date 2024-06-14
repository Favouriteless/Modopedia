package net.favouriteless.modopedia.platform.services;

import net.minecraft.resources.ResourceLocation;
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
     * Register a {@link SimpleJsonResourceReloadListener}, necessary because Fabric requires ReloadListeners to provide
     * an ID (god knows why).
     *
     * <p>Implementations of {@link SimpleJsonResourceReloadListener} should not store data, they should only be used
     * to load data to be used in common.</p>
     *
     * @param id {@link ResourceLocation} ID of the loader, only used by Fabric.
     * @param loader An instance of the ReloadListener.
     */
    void register(ResourceLocation id, SimpleJsonResourceReloadListener loader);

}
