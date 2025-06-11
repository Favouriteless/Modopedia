package net.favouriteless.modopedia.neoforge.datagen.providers;

import net.favouriteless.modopedia.Modopedia;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class MLanguageProvider extends LanguageProvider {

    public MLanguageProvider(PackOutput output) {
        super(output, Modopedia.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("screen.enchanted.error", "Error");
        add("tooltip.modopedia.book_id", "Book ID: %1$s");
        add("item.modopedia.book", "Book (No ID)");
        add("screen.modopedia.categories", "Chapters");
        add("screen.modopedia.entries", "Entries");
        add("template.modopedia.shaped_recipe", "Shaped Recipe");
        add("template.modopedia.shapeless_recipe", "Shapeless Recipe");
        add("template.modopedia.smelting_recipe", "Smelting Recipe");
        add("template.modopedia.blasting_recipe", "Blasting Recipe");
        add("template.modopedia.smoking_recipe", "Smoking Recipe");
    }

}
