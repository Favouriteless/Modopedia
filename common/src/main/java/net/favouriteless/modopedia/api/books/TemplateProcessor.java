package net.favouriteless.modopedia.api.books;

import net.favouriteless.modopedia.api.Variable.MutableLookup;
import net.minecraft.world.level.Level;

public interface TemplateProcessor {

    void init(MutableLookup lookup);

    void refreshData(Level level, MutableLookup lookup);

}
