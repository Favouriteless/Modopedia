package net.favouriteless.modopedia.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import net.favouriteless.modopedia.api.ModopediaApi.EntryAssociation;
import net.favouriteless.modopedia.book.registries.client.ItemAssociationRegistry;
import net.favouriteless.modopedia.client.BookOpenHandler;
import net.favouriteless.modopedia.client.ModopediaClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin {

    @Shadow @Nullable protected Slot hoveredSlot;

    @Inject(method = "tick", at = @At("HEAD"))
    @Debug(export = true)
    private void containerTick(CallbackInfo ci) {
        String lang = Minecraft.getInstance().options.languageCode;

        ItemStack hovered = hoveredSlot != null ? hoveredSlot.getItem() : null;
        EntryAssociation association = hovered != null ? ItemAssociationRegistry.getAssociation(lang, BuiltInRegistries.ITEM.getKey(hovered.getItem())) : null;

        if(association != null && InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), ModopediaClient.KEY_MAPPING.getDefaultKey().getValue())) {
            ModopediaClient.STUDY_TICKS++;

            if(ModopediaClient.STUDY_TICKS > ModopediaClient.STUDY_TICKS_MAX) {
                ModopediaClient.STUDY_TICKS = 0;
                BookOpenHandler.tryOpenEntry(association.book(), association.entryId());
            }
        }
        else {
            ModopediaClient.STUDY_TICKS--;
            if(ModopediaClient.STUDY_TICKS < 0)
                ModopediaClient.STUDY_TICKS = 0;
        }
    }

}
