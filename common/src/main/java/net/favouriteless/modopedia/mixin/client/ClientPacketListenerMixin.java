package net.favouriteless.modopedia.mixin.client;

import net.favouriteless.modopedia.book.loading.BookContentLoader;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    @Inject(method = "handleUpdateRecipes", at = @At("TAIL"))
    private void handleUpdateRecipes(ClientboundUpdateRecipesPacket packet, CallbackInfo ci) {
        BookContentLoader.reloadAll();
    }

}
