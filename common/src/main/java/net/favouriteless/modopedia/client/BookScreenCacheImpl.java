package net.favouriteless.modopedia.client;

import net.favouriteless.modopedia.api.BookScreenCache;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class BookScreenCacheImpl implements BookScreenCache {

    public static final BookScreenCache INSTANCE = new BookScreenCacheImpl();

    private final Map<ResourceLocation, Map<String, Screen>> screenCache = new HashMap<>();

    @Override
    public void setLastScreen(ResourceLocation book, String langCode, Screen screen) {
        screenCache.computeIfAbsent(book, k -> new HashMap<>()).put(langCode, screen);
    }

    @Override
    public Screen getLastScreen(ResourceLocation book, String langCode) {
        return screenCache.containsKey(book) ? screenCache.get(book).get(langCode) : null;
    }

    public void remove(ResourceLocation book) {
        screenCache.remove(book);
    }

    public void clear() {
        screenCache.clear();
    }

}
