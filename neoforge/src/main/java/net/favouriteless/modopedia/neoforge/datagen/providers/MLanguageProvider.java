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
        add("item", "book", "Book (No ID)");

        add("tooltip", "book_id", "Book ID: %1$s");
        add("tooltip", "study", "Hold [%1$s] to Study");
        add("tooltip", "category_link", "Go to category");
        add("tooltip", "entry_link", "Go to entry");
        add("tooltip", "url", "Go to URL: %1$s");

        add("screen", "error", "Error");
        add("screen", "categories", "Chapters");
        add("screen", "entries", "Entries");
        add("screen", "config_preview", "Configure Preview");
        add("screen", "config_preview.x", "Multiblock X");
        add("screen", "config_preview.y", "Multiblock Y");
        add("screen", "config_preview.z", "Multiblock Z");
        add("screen", "config_preview.remove", "Remove");

        add("template", "shaped_recipe", "Shaped Recipe");
        add("template", "shapeless_recipe", "Shapeless Recipe");
        add("template", "recipe", "Recipe");
        add("template", "smelting_recipe", "Smelting Recipe");
        add("template", "blasting_recipe", "Blasting Recipe");
        add("template", "smoking_recipe", "Smoking Recipe");
        add("template", "crafting_recipe", "Crafting Recipe");
        add("template", "cooking_recipe", "Cooking Recipe");

        add("subtitle", "book_flip", "Page Turning");
        add("subtitle", "book_open", "Book Opening");

        add("modopedia.study", "Study");
    }
    
    protected void add(String prefix, String suffix, String translation) {
        add(Modopedia.translation(prefix, suffix), translation);
    }

}
