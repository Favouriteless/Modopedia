package net.favouriteless.modopedia.api.books;

import net.favouriteless.modopedia.api.Lookup.MutableLookup;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.minecraft.world.level.Level;

@FunctionalInterface
public interface TemplateProcessor {

    /**
     * Called after a template's {@link PageComponent}s have been created, but before they have been initialised.
     *
     * @param lookup Lookup belonging to the template, remote variables will pull from this.
     * @param level Level access for whatever.
     */
    void init(MutableLookup lookup, Level level);

}
