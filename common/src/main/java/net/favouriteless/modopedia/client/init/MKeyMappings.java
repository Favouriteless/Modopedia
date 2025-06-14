package net.favouriteless.modopedia.client.init;

import com.mojang.blaze3d.platform.InputConstants;
import net.favouriteless.modopedia.platform.ClientServices;
import net.favouriteless.modopedia.platform.services.IClientRegistryHelper.KeyConflictContext;
import net.minecraft.client.KeyMapping;

public class MKeyMappings {

    public static KeyMapping KEY_STUDY = ClientServices.CLIENT_REGISTRY.createKeyMapping("study", InputConstants.KEY_LCONTROL, KeyMapping.CATEGORY_INVENTORY, KeyConflictContext.GUI);

}
