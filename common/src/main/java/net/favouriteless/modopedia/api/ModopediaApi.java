package net.favouriteless.modopedia.api;

import net.favouriteless.modopedia.ModopediaApiImpl;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.api.books.TemplateProcessor;
import net.favouriteless.modopedia.api.text.TextFormatter;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public interface ModopediaApi {

    static ModopediaApi get() {
        return ModopediaApiImpl.INSTANCE;
    }

    /**
     * Register a processor for a template. This should be called during mod initialisation, but anywhere is fine as
     * long as it's before books start loading.
     *
     * @param id The template ID to register a processor for.
     * @param processor
     */
    void registerTemplateProcessor(ResourceLocation id, TemplateProcessor processor);

    /**
     * Register a new text formatting tag. This should be called during mod initialisation, but anywhere is fine as
     * long as it's before books start loading.
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
