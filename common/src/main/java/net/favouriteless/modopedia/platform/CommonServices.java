package net.favouriteless.modopedia.platform;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.platform.services.ICommonRegistryHelper;
import net.favouriteless.modopedia.platform.services.IPlatformHelper;

import java.util.ServiceLoader;

public class CommonServices {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final ICommonRegistryHelper COMMON_REGISTRY = load(ICommonRegistryHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Modopedia.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }

}