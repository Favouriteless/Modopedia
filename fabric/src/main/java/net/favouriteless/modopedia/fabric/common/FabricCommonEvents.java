package net.favouriteless.modopedia.fabric.common;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.favouriteless.modopedia.common.CommonEvents;
import net.minecraft.server.level.ServerPlayer;

public class FabricCommonEvents {

    public static void register() {
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register(FabricCommonEvents::onDatapackSync);
    }

    public static void onDatapackSync(ServerPlayer player, boolean joined) {
        CommonEvents.datapackSync(player);
    }

}
