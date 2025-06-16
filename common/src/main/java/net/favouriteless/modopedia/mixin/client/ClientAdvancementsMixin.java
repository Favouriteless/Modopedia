package net.favouriteless.modopedia.mixin.client;

import net.favouriteless.modopedia.client.screens.books.BookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Forces the currently opened {@link BookScreen} to re-initialise it's widgets to check for advancement unlocks.
 */
@Mixin(ClientAdvancements.class)
public class ClientAdvancementsMixin {
    
    @Inject(method = "update", at = @At("HEAD"))
    public void update(ClientboundUpdateAdvancementsPacket packet, CallbackInfo ci) {
        if(Minecraft.getInstance().screen instanceof BookScreen screen)
            screen.init(Minecraft.getInstance(), screen.width, screen.height);
    }
    
}
