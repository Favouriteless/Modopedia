package net.favouriteless.modopedia.mixin.client;

import net.favouriteless.modopedia.book.StudyManager;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin {

    @Shadow @Nullable protected Slot hoveredSlot;

    @Inject(method = "tick", at = @At("HEAD"))
    private void containerTick(CallbackInfo ci) {
        ItemStack hovered = hoveredSlot != null ? hoveredSlot.getItem() : null;
        if(hovered != null)
            StudyManager.study(hovered.getItem());
    }

    @Inject(method = "removed", at = @At("HEAD"))
    private void removed(CallbackInfo ci) {
        StudyManager.stopStudying();
    }

}
