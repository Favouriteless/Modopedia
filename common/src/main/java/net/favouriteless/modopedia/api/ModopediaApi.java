package net.favouriteless.modopedia.api;

import net.favouriteless.modopedia.ModopediaApiImpl;
import net.favouriteless.modopedia.api.book.Entry;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public interface ModopediaApi {

    static ModopediaApi get() {
        return ModopediaApiImpl.INSTANCE;
    }

    /**
     * Attempt to find an entry association for a given item and language code.
     *
     * @return The association if found, otherwise null.
     */
    @Nullable EntryAssociation getAssociation(String language, ResourceLocation item);



    record EntryAssociation(ResourceLocation book, String entryId, Entry entry) {}

}
