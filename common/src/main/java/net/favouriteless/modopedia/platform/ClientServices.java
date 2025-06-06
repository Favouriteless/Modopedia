package net.favouriteless.modopedia.platform;

import net.favouriteless.modopedia.platform.services.IClientPlatformHelper;
import net.favouriteless.modopedia.platform.services.IClientRegistryHelper;

// Client services are separated because the dedi servers crash.
public class ClientServices {

    public static final IClientRegistryHelper CLIENT_REGISTRY = CommonServices.load(IClientRegistryHelper.class);
    public static final IClientPlatformHelper PLATFORM = CommonServices.load(IClientPlatformHelper.class);

}
