package net.favouriteless.modopedia.client;

import net.favouriteless.modopedia.api.ScreenCache;
import net.favouriteless.modopedia.client.screens.books.BookScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ScreenCacheImpl implements ScreenCache {

    public static final ScreenCacheImpl INSTANCE = new ScreenCacheImpl();

    private final Map<ResourceLocation, Map<String, BookScreen>> screenCache = new HashMap<>();

    @Override
    public void setLastScreen(ResourceLocation book, String language, BookScreen screen) {
        screenCache.computeIfAbsent(book, k -> new HashMap<>()).put(language, screen);
    }

    @Override
    public BookScreen getLastScreen(ResourceLocation book, String language) {
        return screenCache.containsKey(book) ? screenCache.get(book).get(language) : null;
    }

    public void remove(ResourceLocation book) {
        screenCache.remove(book);
    }

    public void clear() {
        screenCache.clear();
    }

}
