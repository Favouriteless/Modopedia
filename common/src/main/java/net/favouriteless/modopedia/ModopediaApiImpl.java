package net.favouriteless.modopedia;

import net.favouriteless.modopedia.api.ModopediaApi;

public class ModopediaApiImpl implements ModopediaApi {

    public static final ModopediaApi INSTANCE = new ModopediaApiImpl();

    private ModopediaApiImpl() {}

}
