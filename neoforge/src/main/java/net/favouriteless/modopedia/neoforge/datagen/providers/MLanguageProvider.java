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
        add(Modopedia.translation("item", "book"), "Book (No ID)");

        add(Modopedia.translation("tooltip", "book_id"), "Book ID: %1$s");
        add(Modopedia.translation("tooltip", "study"), "Hold [%1$s] to Study");
        add(Modopedia.translation("tooltip", "category_link"), "Go to category");
        add(Modopedia.translation("tooltip", "entry_link"), "Go to entry");

        add(Modopedia.translation("screen", "error"), "Error");
        add(Modopedia.translation("screen", "categories"), "Chapters");
        add(Modopedia.translation("screen", "entries"), "Entries");

        add(Modopedia.translation("template", "shaped_recipe"), "Shaped Recipe");
        add(Modopedia.translation("template", "shapeless_recipe"), "Shapeless Recipe");
        add(Modopedia.translation("template", "smelting_recipe"), "Smelting Recipe");
        add(Modopedia.translation("template", "blasting_recipe"), "Blasting Recipe");
        add(Modopedia.translation("template", "smoking_recipe"), "Smoking Recipe");

        add(Modopedia.translation("subtitle", "book_flip"), "Page Turning");
        add(Modopedia.translation("subtitle", "book_open"), "Book Opening");

        add("modopedia.study", "Study");
    }

}
