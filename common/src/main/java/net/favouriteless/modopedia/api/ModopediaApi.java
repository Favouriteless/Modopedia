package net.favouriteless.modopedia.api;

import net.favouriteless.modopedia.ModopediaApiImpl;

public interface ModopediaApi {

    static ModopediaApi get() {
        return ModopediaApiImpl.INSTANCE;
    }

}
