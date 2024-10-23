package net.favouriteless.modopedia.platform.services;

import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.item.Item;

public interface IClientRegistryHelper {

    /**
     * Register an item model predicate.
     * @param item The {@link Item} the predicate is for.
     * @param location The {@link ResourceLocation} of the predicate.
     * @param function {@link ItemPropertyFunction} determining if the predicate is true or not.
     */
    void register(Item item, ResourceLocation location, ClampedItemPropertyFunction function);

    /**
     * Register a {@link PreparableReloadListener}, necessary because Fabric requires ReloadListeners to provide
     * an ID (god knows why).
     *
     * @param id {@link ResourceLocation} ID of the loader, only used by Fabric.
     * @param loader An instance of the ReloadListener.
     */
    PreparableReloadListener registerReloadListener(ResourceLocation id, PreparableReloadListener loader);

}
