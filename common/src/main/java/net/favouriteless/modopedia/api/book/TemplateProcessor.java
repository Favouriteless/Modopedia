package net.favouriteless.modopedia.api.book;

import net.favouriteless.modopedia.api.Lookup.MutableLookup;
import net.favouriteless.modopedia.api.book.page_components.PageComponent;
import net.minecraft.world.level.Level;

@FunctionalInterface
public interface TemplateProcessor {

    /**
     * Called after a template's {@link PageComponent}s have been created, but before they have been initialised.
     *
     * @param book Book this instance of the template is being used in.
     * @param lookup Lookup belonging to the template, remote variables will pull from this.
     * @param level Level access for whatever.
     */
    void init(Book book, MutableLookup lookup, Level level);

}
