package net.favouriteless.modopedia.api;

import net.favouriteless.modopedia.ModopediaApiImpl;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.api.text.TextFormatter;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public interface ModopediaApi {

    static ModopediaApi get() {
        return ModopediaApiImpl.INSTANCE;
    }

    /**
     * Register a new text formatting tag. This should be called during mod initialisation.
     */
    void registerTextFormatter(TextFormatter formatter);

    /**
     * Attempt to find an entry association for a given item and language code.
     *
     * @return The association if found, otherwise null.
     */
    @Nullable EntryAssociation getAssociation(String language, ResourceLocation item);



    record EntryAssociation(ResourceLocation book, String entryId, Entry entry) {}

}
