package net.favouriteless.modopedia.platform.services;

import net.minecraft.client.KeyMapping;
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

    /**
     * Register a {@link KeyMapping}.
     *
     * @param name The name translation key of the {@link KeyMapping} being created.
     * @param keyCode Unicode keycode of the default key for this {@link KeyMapping}.
     * @param category The category translation key of this {@link KeyMapping}.
     * @param conflictContext {@link KeyConflictContext} representing Forge's enum by the same name, only used by Forge.
     *                        This is used to determine key conflicts.
     *
     * @return The {@link KeyMapping} after it has been registered.
     */
    KeyMapping createKeyMapping(String name, int keyCode, String category, KeyConflictContext conflictContext);

    /**
     * Represents NeoForge's enum of the same name, so this can actually compile. NeoForge implementation will grab the ordinal
     * of this and convert it into its own KeyConflictContext.
     */
    enum KeyConflictContext {
        UNIVERSAL,
        GUI,
        IN_GAME
    }

}
