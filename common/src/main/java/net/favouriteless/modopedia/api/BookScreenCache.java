package net.favouriteless.modopedia.api;

import net.favouriteless.modopedia.client.BookScreenCacheImpl;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

/**
 * Stores the most recently open {@link Screen} for each book, per language. Will get cleared when the book reloads.
 */
public interface BookScreenCache {

    static BookScreenCache get() {
        return BookScreenCacheImpl.INSTANCE;
    }

    void setLastScreen(ResourceLocation book, String langCode, Screen screen);

    Screen getLastScreen(ResourceLocation book, String langCode);

}
