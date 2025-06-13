package net.favouriteless.modopedia.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.favouriteless.modopedia.client.init.MBookScreenFactories;
import net.favouriteless.modopedia.client.init.MPageComponents;
import net.favouriteless.modopedia.client.init.MTextFormatters;
import net.favouriteless.modopedia.platform.ClientServices;
import net.favouriteless.modopedia.platform.services.IClientRegistryHelper.KeyConflictContext;
import net.minecraft.client.KeyMapping;

public class ModopediaClient {

    public static final KeyMapping KEY_STUDY = ClientServices.CLIENT_REGISTRY.register("study", InputConstants.KEY_LCONTROL,
            KeyMapping.CATEGORY_INVENTORY, KeyConflictContext.GUI);

    public static void init() {
        MTextFormatters.load();
        MPageComponents.load();
        MBookScreenFactories.load();
    }

}
