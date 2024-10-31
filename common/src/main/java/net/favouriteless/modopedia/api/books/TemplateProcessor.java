package net.favouriteless.modopedia.api.books;

import net.favouriteless.modopedia.api.Variable.MutableLookup;
import net.minecraft.world.level.Level;

@FunctionalInterface
public interface TemplateProcessor {

    void init(MutableLookup lookup, Level level);

}
