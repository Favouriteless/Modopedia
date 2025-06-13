package net.favouriteless.modopedia;

import net.favouriteless.modopedia.api.ModopediaApi;
import net.favouriteless.modopedia.book.registries.client.ItemAssociationRegistry;
import net.minecraft.resources.ResourceLocation;

public class ModopediaApiImpl implements ModopediaApi {

    public static final ModopediaApi INSTANCE = new ModopediaApiImpl();

    private ModopediaApiImpl() {}

    @Override
    public EntryAssociation getAssociation(String language, ResourceLocation item) {
        return ItemAssociationRegistry.getAssociation(language, item);
    }

}
